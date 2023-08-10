package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.ProjectCreateUpdateDTO;
import by.it_academy.jd2.core.dto.UserRefDTO;
import by.it_academy.jd2.dao.entity.ProjectEntity;
import by.it_academy.jd2.dao.repositories.IProjectDao;
import by.it_academy.jd2.service.api.IProjectService;
import by.it_academy.jd2.service.supportservices.UserHolder;
import org.example.mylib.tm.itacademy.exceptions.EntityNotFoundException;
import org.example.mylib.tm.itacademy.exceptions.UpdateEntityException;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectService implements IProjectService {
    private static final String PROJECT_UPDATER = "Updating project data";
    private static final String PROJECT_NOT_FOUND = "Project is not found";
    private static final String NEW_PROJECT_CREATED = "Creating a new project under a different project";
    private static final String PROJECT_UPDATED = "Project updated! Try again";
    private static final String NEW_PROJECT_OPENING = "New project opening";
    private static final String REQUESTED_DATA_UUID = "Requested project data by UUID";

    private final IProjectDao projectDao;
    private final ConversionService conversionService;
    private final UserHolder userHolder;


    public ProjectService(IProjectDao projectDao,
                          ConversionService conversionService,
                          UserHolder userHolder) {
        this.projectDao = projectDao;
        this.conversionService = conversionService;
        this.userHolder = userHolder;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProjectEntity> getAll(PageRequest pageRequest) {
        return this.projectDao.findAll(pageRequest);
    }

    @Transactional(readOnly = true)
    @Override
    public ProjectEntity get(UUID uuid) {
        ProjectEntity entity = this.projectDao
                .findById(uuid)
                .orElseThrow(()
                        -> new EntityNotFoundException(PROJECT_NOT_FOUND));

//        this.auditService.send(getMe(), REQUESTED_DATA_UUID, uuid.toString());
        // TODO: 09.08.2023 аудит

        return entity;
    }

    @Transactional
    @Override
    public ProjectEntity save(ProjectCreateUpdateDTO item) {
        ProjectEntity entity = Objects.requireNonNull(
                conversionService
                        .convert(item, ProjectEntity.class));
        entity.setUuid(UUID.randomUUID());

        this.projectDao.save(entity);
//        this.auditService.send(getMe(), NEW_PROJECT_CREATED, entity.getUuid().toString());
        // TODO: 09.08.2023 аудит
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
            throw new UpdateEntityException(PROJECT_UPDATER);
        }

        entity.setName(item.getName());
        entity.setDescription(item.getDescription());
        entity.setManager(item.getManager().getUuid());
        entity.setStaff(item.getStaff().stream().map(UserRefDTO::getUuid).collect(Collectors.toList()));
        entity.setStatus(item.getStatus());

        ProjectEntity saveEntity = this.projectDao.save(entity);

//        this.auditService.send(getMe(), PROJECT_UPDATER, entity.getUuid().toString());
        // TODO: 09.08.2023 аудит
        return saveEntity;
    }

    // TODO: 09.08.2023 получить данные о текущем пользователе
//    @Transactional(readOnly = true)
//    @Override
//    public ProjectEntity getMe() {
//        return this.projectDao
//                .findByMail(this.userHolder
//                        .getUser()
//                        .getUsername())
//                .orElseThrow(()
//                        -> new EntityNotFoundException(USER_NOT_FOUND));
//    }
}
