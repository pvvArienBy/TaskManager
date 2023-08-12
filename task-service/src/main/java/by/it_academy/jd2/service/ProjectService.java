package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.*;
import by.it_academy.jd2.core.enums.EProjectStatus;
import by.it_academy.jd2.dao.entity.ProjectEntity;
import by.it_academy.jd2.dao.repositories.IProjectDao;
import by.it_academy.jd2.service.api.IAuditService;
import by.it_academy.jd2.service.api.IProjectService;
import by.it_academy.jd2.service.api.feign.IUserClientService;
import by.it_academy.jd2.service.supportservices.JwtService;
import by.it_academy.jd2.service.supportservices.UserHolder;
import org.example.mylib.tm.itacademy.enums.EssenceType;
import org.example.mylib.tm.itacademy.exceptions.EntityNotFoundException;
import org.example.mylib.tm.itacademy.exceptions.UpdateEntityException;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService implements IProjectService {
    private static final String PROJECT_UPDATER = "Updating project data";
    private static final String PROJECT_NOT_FOUND = "Project is not found";
    private static final String NEW_PROJECT_CREATED = "Creating a new project under a different project";
    private static final String PROJECT_UPDATED = "Project updated! Try again";
    private static final String REQUESTED_DATA_UUID = "Requested project data by UUID";
    private static final String RESULT_NOT_FOUND = "RESULT is not found";

    private final IProjectDao projectDao;
    private final IAuditService auditService;
    private final IUserClientService userClientService;
    private final ConversionService conversionService;
    private final UserHolder userHolder;
    private final JwtService jwtService;

    public ProjectService(IProjectDao projectDao, IAuditService auditService,
                          IUserClientService userClientService, ConversionService conversionService,
                          UserHolder userHolder, JwtService jwtService) {
        this.projectDao = projectDao;
        this.auditService = auditService;
        this.userClientService = userClientService;
        this.conversionService = conversionService;
        this.userHolder = userHolder;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProjectEntity> getAll(PageRequest pageRequest, Boolean archived) {
        if (!userHolder.checkAdminRole()) {
            UUID meUuid = UUID.fromString(this.jwtService
                    .extractUuid(userHolder.getToken()));

            if (archived) {
                return this.projectDao
                        .findByManagerOrStaff(meUuid, meUuid, pageRequest);
            }

            return this.projectDao
                    .findByManagerOrStaffOrAndStatusIsNotLike(meUuid, meUuid, EProjectStatus.ARCHIVE, pageRequest);
        }

        if (archived) {
            return this.projectDao.findAll(pageRequest);
        }
        return this.projectDao.findByStatusNotLike(EProjectStatus.ARCHIVE, pageRequest);
    }

    @Transactional(readOnly = true)
    @Override
    public ProjectEntity get(UUID uuid) {
        ProjectEntity entity = this.projectDao
                .findById(uuid)
                .orElseThrow(()
                        -> new EntityNotFoundException(PROJECT_NOT_FOUND));

        this.auditService.send(REQUESTED_DATA_UUID, uuid.toString(), EssenceType.PROJECT);

        return entity;
    }

    @Transactional
    @Override
    public ProjectEntity save(ProjectCreateUpdateDTO item) {
        checkUsers(item);
        ProjectEntity entity = Objects.requireNonNull(
                conversionService
                        .convert(item, ProjectEntity.class));
        entity.setUuid(UUID.randomUUID());

        this.projectDao.saveAndFlush(entity);
        this.auditService.send(
                NEW_PROJECT_CREATED, entity.getUuid().toString(), EssenceType.PROJECT);
        return entity;
    }

    @Transactional
    @Override
    public ProjectEntity update(UUID uuid, LocalDateTime version, ProjectCreateUpdateDTO item) {
        ProjectEntity entity = projectDao
                .findById(uuid)
                .orElseThrow(()
                        -> new EntityNotFoundException(PROJECT_NOT_FOUND));

        if (!version.equals(entity.getDtUpdate())) {
            throw new UpdateEntityException(PROJECT_UPDATED);
        }

        entity.setName(item.getName());
        entity.setDescription(item.getDescription());
        entity.setManager(item.getManager().getUuid());
        entity.setStaff(item.getStaff().stream().map(UserRefDTO::getUuid).collect(Collectors.toList()));
        entity.setStatus(item.getStatus());

        ProjectEntity saveEntity = this.projectDao.saveAndFlush(entity);
        this.auditService.send(PROJECT_UPDATER, uuid.toString(), EssenceType.PROJECT);

        return saveEntity;
    }

    private void checkUsers(ProjectCreateUpdateDTO item) {
        UsersVerificationDTO usersVerificationDTO = new UsersVerificationDTO();
        usersVerificationDTO.setManager(item.getManager().getUuid());
        usersVerificationDTO.setStaff(item.getStaff().stream().map(UserRefDTO::getUuid).collect(Collectors.toList()));
        ResultUsersVerificationDTO resultDTO = Optional
                .ofNullable(
                        this.userClientService
                                .checkForProject(usersVerificationDTO)
                                .getBody())
                .orElseThrow(()
                        -> new ResultNotFoundException(RESULT_NOT_FOUND));


        Map<String, String> errorMap = new HashMap<>();  //// TODO: 11.08.2023 в отдельный метод

        if (!resultDTO.isManagerCheck()) {
            errorMap.put("manager.field", "not found in the system");
        }
        if (!resultDTO.isManagerCheckRole()) {
            errorMap.put("manager.role_field", "The user does not have the specified role");
        }
        if (!resultDTO.isListUsersCheck()) {
            errorMap.put("staff.field", "the list has invalid users");
        }
        if (!errorMap.isEmpty()) {
            throw new CustomValidationException(errorMap);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<UUID> getMyList() {
        UUID meUuid = UUID.fromString(
                this.jwtService.extractUuid(userHolder.getToken()));

        return this.projectDao.findByManagerOrStaff(meUuid, meUuid).stream()
                .map(ProjectEntity::getUuid)
                .toList();
    }

    @Override
    public boolean existsByUuidAndStaffContaining(UUID projectUuid, UUID staffUuid) {
        return this.projectDao.existsByUuidAndStaffContaining(projectUuid, staffUuid);
    }

    @Override
    public boolean existsByManager(UUID managerUuid) {
        return this.projectDao.existsByManager(managerUuid);
    }

    @Override
    public boolean existsById(UUID uuid) {
        return this.projectDao.existsById(uuid);
    }
}
