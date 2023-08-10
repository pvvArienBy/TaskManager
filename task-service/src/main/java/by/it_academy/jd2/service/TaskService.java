package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.TaskCreateUpdateDTO;
import by.it_academy.jd2.core.enums.ETaskStatus;
import by.it_academy.jd2.dao.entity.TaskEntity;
import by.it_academy.jd2.dao.repositories.ITaskDao;
import by.it_academy.jd2.service.api.ITaskService;
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

@Service
public class TaskService implements ITaskService {
    private static final String TASK_UPDATER = "Updating task data";
    private static final String TASK_NOT_FOUND = "Task is not found";
    private static final String NEW_TASK_CREATED = "Creating a new task under a different task";
    private static final String TASK_UPDATED = "Task updated! Try again";
    private static final String REQUESTED_DATA_UUID = "Requested task data by UUID";

    private final ITaskDao taskDao;
    private final ConversionService conversionService;
    private final UserHolder userHolder;

    public TaskService(ITaskDao taskDao,
                       ConversionService conversionService,
                       UserHolder userHolder) {
        this.taskDao = taskDao;
        this.conversionService = conversionService;
        this.userHolder = userHolder;
    }


    @Transactional(readOnly = true)
    @Override
    public Page<TaskEntity> getAll(PageRequest pageRequest) {
        return this.taskDao.findAll(pageRequest);
    }

    @Transactional(readOnly = true)
    @Override
    public TaskEntity get(UUID uuid) {
        TaskEntity entity = this.taskDao
                .findById(uuid)
                .orElseThrow(()
                        -> new EntityNotFoundException(TASK_NOT_FOUND));

//        this.auditService.send(getMe(), REQUESTED_DATA_UUID, uuid.toString());
// TODO: 09.08.2023 аудит
        return entity;
    }

    @Transactional
    @Override
    public TaskEntity save(TaskCreateUpdateDTO item) {
        TaskEntity entity = Objects.requireNonNull(
                conversionService
                        .convert(item, TaskEntity.class));
        entity.setUuid(UUID.randomUUID());

        this.taskDao.save(entity);
//        this.auditService.send(getMe(), NEW_TASK_CREATED, entity.getUuid().toString());
// TODO: 09.08.2023 аудит
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
            throw new UpdateEntityException(TASK_UPDATER);
        }

        entity.setProject(item.getProject().getUuid());
        entity.setTitle(item.getTitle());
        entity.setDescription(item.getDescription());
        entity.setStatus(item.getStatus());
        entity.setImplementer(item.getImplementer().getUuid());

        TaskEntity saveEntity = this.taskDao.save(entity);

//        this.auditService.send(getMe(), PROJECT_UPDATER, entity.getUuid().toString());
        // TODO: 09.08.2023 аудит
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
            throw new UpdateEntityException(TASK_UPDATER);
        }

        entity.setStatus(status);

        TaskEntity saveEntity = this.taskDao.save(entity);

//        this.auditService.send(getMe(), PROJECT_UPDATER, entity.getUuid().toString());
        // TODO: 09.08.2023 аудит
        return saveEntity;
    }
}
