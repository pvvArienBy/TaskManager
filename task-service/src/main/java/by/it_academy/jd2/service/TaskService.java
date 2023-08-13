package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.TaskCreateUpdateDTO;
import by.it_academy.jd2.core.enums.ETaskStatus;
import by.it_academy.jd2.dao.entity.TaskEntity;
import by.it_academy.jd2.dao.repositories.ITaskDao;
import by.it_academy.jd2.service.api.IAuditService;
import by.it_academy.jd2.service.api.IProjectService;
import by.it_academy.jd2.service.api.ITaskService;
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
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
public class TaskService implements ITaskService {
    private static final String TASK_UPDATER = "Updating task data";
    private static final String TASK_NOT_FOUND = "Task is not found";
    private static final String NEW_TASK_CREATED = "Creating a new task under a different task";
    private static final String TASK_UPDATED = "Task updated! Try again";
    private static final String REQUESTED_DATA_UUID = "Requested task data by UUID";

    private final ITaskDao taskDao;
    private final ConversionService conversionService;
    private final IAuditService auditService;
    private final IProjectService projectService;
    private final UserHolder userHolder;

    public TaskService(ITaskDao taskDao,
                       ConversionService conversionService, IAuditService auditService,
                       IProjectService projectService, UserHolder userHolder) {
        this.taskDao = taskDao;
        this.conversionService = conversionService;
        this.auditService = auditService;
        this.projectService = projectService;
        this.userHolder = userHolder;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TaskEntity> getAll(PageRequest pageRequest,
                                   List<UUID> project,
                                   List<UUID> implementer,
                                   List<ETaskStatus> status) {

        if (!userHolder.checkAdminRole()) {
            List<UUID> result = projectService.getMyList();
            project.removeIf(uuid -> !result.contains(uuid));

            if (project.isEmpty() && implementer.isEmpty() && status.isEmpty()) {
                return taskDao.findByProjectIn(result, pageRequest);
            }
        }

        if (!project.isEmpty() && implementer.isEmpty() && status.isEmpty()) {
            return taskDao.findByProjectIn(project, pageRequest);
        }

        if (project.isEmpty() && !implementer.isEmpty() && status.isEmpty()) {
            return taskDao.findByImplementerIn(implementer, pageRequest);
        }

        if (!project.isEmpty() && !implementer.isEmpty() && status.isEmpty()) {
            return taskDao.findByProjectInAndImplementerIn(project, implementer, pageRequest);
        }

        if (project.isEmpty() && implementer.isEmpty() && !status.isEmpty()) {
            return taskDao.findByStatusIn(status, pageRequest);
        }

        if (!project.isEmpty() && implementer.isEmpty() && !status.isEmpty()) {
            return taskDao.findByProjectInAndStatusIn(project, status, pageRequest);
        }

        if (project.isEmpty() && !implementer.isEmpty() && !status.isEmpty()) {
            return taskDao.findByImplementerInAndStatusIn(implementer, status, pageRequest);
        }

        if (!project.isEmpty() && !implementer.isEmpty() && !status.isEmpty()) {
            return taskDao.findByProjectInAndImplementerInAndStatusIn(project, implementer, status, pageRequest);
        }

        return taskDao.findAll(pageRequest);
    }

    @Transactional(readOnly = true)
    @Override
    public TaskEntity get(UUID uuid) {
        TaskEntity entity = this.taskDao
                .findById(uuid)
                .orElseThrow(()
                        -> new EntityNotFoundException(TASK_NOT_FOUND));

        this.projectService.get(entity.getProject());

        this.auditService.send(REQUESTED_DATA_UUID, uuid.toString(), EssenceType.TASK);

        return entity;
    }

    @Transactional
    @Override
    public TaskEntity save(TaskCreateUpdateDTO item) {
        this.projectService.checkInProject(item);

        if (!userHolder.checkAdminRole()) {
            this.projectService.get(item.getProject().getUuid());
        }

        TaskEntity entity = Objects.requireNonNull(
                conversionService
                        .convert(item, TaskEntity.class));
        entity.setUuid(UUID.randomUUID());

        this.taskDao.saveAndFlush(entity);
        this.auditService.send(
                NEW_TASK_CREATED, entity.getUuid().toString(), EssenceType.TASK);

        return entity;
    }

    @Transactional
    @Override
    public TaskEntity update(UUID uuid, LocalDateTime version, TaskCreateUpdateDTO item) {
        this.projectService.checkInProject(item);

        if (!userHolder.checkAdminRole()) {
            this.projectService.get(item.getProject().getUuid());
        }

        TaskEntity entity = taskDao
                .findById(uuid)
                .orElseThrow(()
                        -> new EntityNotFoundException(TASK_NOT_FOUND));

        if (!version.equals(entity.getDtUpdate())) {
            throw new UpdateEntityException(TASK_UPDATED);
        }

        entity.setProject(item.getProject().getUuid());
        entity.setTitle(item.getTitle());
        entity.setDescription(item.getDescription());
        entity.setStatus(item.getStatus());
        entity.setImplementer(item.getImplementer().getUuid());

        TaskEntity saveEntity = this.taskDao.saveAndFlush(entity);
        this.auditService.send(TASK_UPDATER, uuid.toString(), EssenceType.TASK);

        return saveEntity;
    }

    @Transactional
    @Override
    public TaskEntity update(UUID uuid, LocalDateTime version, ETaskStatus status) {
        TaskEntity entity = taskDao
                .findById(uuid)
                .orElseThrow(()
                        -> new EntityNotFoundException(TASK_NOT_FOUND));

        if (!version.equals(entity.getDtUpdate())) {
            throw new UpdateEntityException(TASK_UPDATED);
        }

        entity.setStatus(status);

        TaskEntity saveEntity = this.taskDao.saveAndFlush(entity);
        this.auditService.send(TASK_UPDATER, uuid.toString(), EssenceType.TASK);

        return saveEntity;
    }
}
