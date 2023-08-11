package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.CustomValidationException;
import by.it_academy.jd2.core.dto.TaskCreateUpdateDTO;
import by.it_academy.jd2.core.enums.ETaskStatus;
import by.it_academy.jd2.dao.entity.TaskEntity;
import by.it_academy.jd2.dao.repositories.IProjectDao;
import by.it_academy.jd2.dao.repositories.ITaskDao;
import by.it_academy.jd2.service.api.IAuditService;
import by.it_academy.jd2.service.api.ITaskService;
import org.example.mylib.tm.itacademy.enums.EssenceType;
import org.example.mylib.tm.itacademy.exceptions.EntityNotFoundException;
import org.example.mylib.tm.itacademy.exceptions.UpdateEntityException;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

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
    private final IProjectDao projectDao;

    public TaskService(ITaskDao taskDao,
                       ConversionService conversionService,
                       IAuditService auditService, IProjectDao projectDao) {
        this.taskDao = taskDao;
        this.conversionService = conversionService;
        this.auditService = auditService;
        this.projectDao = projectDao;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TaskEntity> getAll(PageRequest pageRequest, List<UUID> project) {
        if (project != null && !project.isEmpty()) {
            Page<TaskEntity> tasks =  this.taskDao.findByProjectUuids(project, pageRequest);
            if (tasks.isEmpty()) {
                return Page.empty(pageRequest);
            }
        }

        return this.taskDao.findAll(pageRequest);
    }

    @Transactional(readOnly = true)
    @Override
    public TaskEntity get(UUID uuid) {
        TaskEntity entity = this.taskDao
                .findById(uuid)
                .orElseThrow(()
                        -> new EntityNotFoundException(TASK_NOT_FOUND));

        this.auditService.send(REQUESTED_DATA_UUID, uuid.toString(), EssenceType.TASK);

        return entity;
    }

    @Transactional
    @Override
    public TaskEntity save(TaskCreateUpdateDTO item) {
        check(item);
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

    private void check(TaskCreateUpdateDTO item) {

        Map<String, String> errorMap = new HashMap<>();  //// TODO: 11.08.2023 в отдельный метод

        if (!this.projectDao.existsById(item.getProject().getUuid())) {
            errorMap.put("project.field", "not found in the system");
        }

        if (item.getImplementer() != null) {
            if (!this.projectDao.existsByUuidAndStaffContaining(
                    item.getProject().getUuid(), item.getImplementer().getUuid())) {
                if (!this.projectDao.existsByManager(item.getImplementer().getUuid())) {
                    errorMap.put("implementer.field", "the performer was not found as a member in the specified project!");
                }
            }
        }

        if (!errorMap.isEmpty()) {
            throw new CustomValidationException(errorMap);
        }


//        if (!resultDTO.isListUsersCheck()) {
//            errorMap.put("staff.field", "the list has invalid users");
//        }
//        if (!errorMap.isEmpty()) {
//            throw new CustomValidationException(errorMap);
//        }

//        dto.setListUsersCheck(existsAllByUuidIn(item.getStaff()));
//        dto.setManagerCheck(this.userDao.existsById(item.getManager()));
//        dto.setManagerCheckRole(this.userDao.existsByUuidAndRole(item.getManager(), ERole.MANAGER));

    }
}
