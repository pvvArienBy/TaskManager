package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.ProjectCreateUpdateDTO;
import by.it_academy.jd2.core.dto.RefDTO;
import by.it_academy.jd2.core.dto.TaskCreateUpdateDTO;
import by.it_academy.jd2.core.enums.EProjectStatus;
import by.it_academy.jd2.dao.entity.ProjectEntity;
import by.it_academy.jd2.dao.repositories.IProjectDao;
import by.it_academy.jd2.service.api.IAuditService;
import by.it_academy.jd2.service.api.IProjectService;
import by.it_academy.jd2.service.api.feign.IUserClientService;
import by.it_academy.jd2.service.supportservices.JwtService;
import by.it_academy.jd2.service.supportservices.UserHolder;
import org.example.mylib.tm.itacademy.dto.ResultUsersVerificationDTO;
import org.example.mylib.tm.itacademy.dto.UsersVerificationDTO;
import org.example.mylib.tm.itacademy.enums.EFieldsErrorInfo;
import org.example.mylib.tm.itacademy.enums.EssenceType;
import org.example.mylib.tm.itacademy.exceptions.CustomValidationException;
import org.example.mylib.tm.itacademy.exceptions.EntityNotFoundException;
import org.example.mylib.tm.itacademy.exceptions.ResultNotFoundException;
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
    public static final String NOT_FOUND_MESSAGE = "not found in the system";
    public static final String ROLE_NOT_FOUND_MESSAGE = "The user does not have the specified role";
    public static final String INVALID_USERS_MESSAGE = "the list has invalid users";
    public static final String PERFORMER_NOT_FOUND_MESSAGE = "the performer was not found as a member in the specified project!";


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
                    .findByManagerAndStatusNotLikeOrStaffContainsAndStatusNotLike(meUuid, EProjectStatus.ARCHIVE, meUuid, EProjectStatus.ARCHIVE, pageRequest);
        }

        if (archived) {
            return this.projectDao.findAll(pageRequest);
        }
        return this.projectDao.findByStatusNotLike(EProjectStatus.ARCHIVE, pageRequest);
    }

    @Transactional(readOnly = true)
    @Override
    public ProjectEntity get(UUID uuid) {
        ProjectEntity entity;
        if (!userHolder.checkAdminRole()) {
            UUID meUuid = UUID.fromString(this.jwtService
                    .extractUuid(userHolder.getToken()));
            entity = this.projectDao
                    .findByUuidAndManagerOrUuidAndStaffContains(uuid, meUuid, uuid, meUuid)
                    .orElseThrow(()
                            -> new EntityNotFoundException(PROJECT_NOT_FOUND));

        } else {
            entity = this.projectDao
                    .findById(uuid)
                    .orElseThrow(()
                            -> new EntityNotFoundException(PROJECT_NOT_FOUND));
        }

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
        entity.setStaff(item.getStaff().stream().map(RefDTO::getUuid).collect(Collectors.toList()));
        entity.setStatus(item.getStatus());

        ProjectEntity saveEntity = this.projectDao.saveAndFlush(entity);
        this.auditService.send(PROJECT_UPDATER, uuid.toString(), EssenceType.PROJECT);

        return saveEntity;
    }

    private void checkUsers(ProjectCreateUpdateDTO item) {
        UsersVerificationDTO usersVerificationDTO = new UsersVerificationDTO();
        usersVerificationDTO.setManager(item.getManager().getUuid());
        usersVerificationDTO.setStaff(item.getStaff().stream().map(RefDTO::getUuid).collect(Collectors.toList()));
        ResultUsersVerificationDTO resultDTO = Optional
                .ofNullable(
                        this.userClientService
                                .checkForProject(usersVerificationDTO)
                                .getBody())
                .orElseThrow(()
                        -> new ResultNotFoundException(RESULT_NOT_FOUND));

        Map<String, String> errorMap = new HashMap<>();

        if (!resultDTO.isManagerCheck()) {
            errorMap.put(EFieldsErrorInfo.MANAGER_FIELD.name(), NOT_FOUND_MESSAGE);
        }
        if (!resultDTO.isManagerCheckRole()) {
            errorMap.put(EFieldsErrorInfo.MANAGER_ROLE_FIELD.name(), ROLE_NOT_FOUND_MESSAGE);
        }
        if (!resultDTO.isListUsersCheck()) {
            errorMap.put(EFieldsErrorInfo.STAFF_FILED.name(), INVALID_USERS_MESSAGE);
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
    public void checkInProject(TaskCreateUpdateDTO item) {

        Map<String, String> errorMap = new HashMap<>();

        if (!this.projectDao.existsById(item.getProject().getUuid())) {
            errorMap.put(EFieldsErrorInfo.MANAGER_FIELD.name(), NOT_FOUND_MESSAGE);
        }

        if (item.getImplementer() != null) {
            if (!this.projectDao.existsByUuidAndStaffContaining(
                    item.getProject().getUuid(), item.getImplementer().getUuid())) {
                if (!this.projectDao.existsByManager(item.getImplementer().getUuid())) {
                    errorMap.put(EFieldsErrorInfo.IMPLEMENTER_FILED.name(), PERFORMER_NOT_FOUND_MESSAGE);
                }
            }
        }

        if (!errorMap.isEmpty()) {
            throw new CustomValidationException(errorMap);
        }
    }

//    @Override
//    public boolean checkProjectFromTask(UUID project) {
//        UUID meUuid = UUID.fromString(
//                this.jwtService.extractUuid(userHolder.getToken()));
//
//        return get(project).getManager().equals(meUuid) || get(project).getStaff().contains(meUuid);
//    } // TODO: 12.08.2023 удалить после всех тестов

//    @Override
//    public boolean checkProjectFromTask(UUID project) {
//        return this.get(project) != null;}
}
