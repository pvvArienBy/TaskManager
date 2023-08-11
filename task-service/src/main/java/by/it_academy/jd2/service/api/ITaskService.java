package by.it_academy.jd2.service.api;

import by.it_academy.jd2.core.dto.TaskCreateUpdateDTO;
import by.it_academy.jd2.core.enums.ETaskStatus;
import by.it_academy.jd2.dao.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ITaskService extends ICRUService<TaskEntity, TaskCreateUpdateDTO> {
    Page<TaskEntity> getAll(PageRequest pageRequest, List<UUID> project, List<UUID> implementer);
    TaskEntity update(UUID uuid, LocalDateTime version, ETaskStatus status);

}
