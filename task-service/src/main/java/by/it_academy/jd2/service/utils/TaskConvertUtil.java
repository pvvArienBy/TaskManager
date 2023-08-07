package by.it_academy.jd2.service.utils;

import by.it_academy.jd2.core.dto.TaskDTO;
import by.it_academy.jd2.dao.entity.TaskEntity;

import java.time.ZoneId;
import java.util.Objects;

public class TaskConvertUtil {

    /**
     * Converts a TaskEntity object to a TaskDTO object.
     *
     * @param entity the TaskEntity object to convert
     * @return the converted TaskDTO object
     */
    public static TaskDTO toDTO(TaskEntity entity) {
        Objects.requireNonNull(entity, "The item parameter cannot be null");
        TaskDTO dto = new TaskDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setProject(ProjectConvertUtil.toDTO(entity.getProject()));
        dto.setPerformer(entity.getPerformer());
        dto.setStatus(entity.getStatus());
        dto.setPriority(entity.getPriority());
        dto.setCategory(entity.getCategory());
        dto.setTerm(entity.getTerm());
        dto.setFiles(FileConvertUtil.toDTO(entity.getFiles()));
        dto.setVersion(entity.getUpdateDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        return dto;
    }

    /**
     * Converts a TaskDTO object to a TaskEntity object.
     *
     * @param dto the TaskDTO object to convert
     * @return the converted TaskEntity object
     */
    public static TaskEntity toEntity(TaskDTO dto) {
        Objects.requireNonNull(dto, "The dto parameter cannot be null");
        TaskEntity entity = new TaskEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setProject(ProjectConvertUtil.toEntity(dto.getProject()));
        entity.setPerformer(dto.getPerformer());
        entity.setStatus(dto.getStatus());
        entity.setPriority(dto.getPriority());
        entity.setCategory(dto.getCategory());
        entity.setTerm(dto.getTerm());
        entity.setFiles(FileConvertUtil.toEntity(dto.getFiles()));

        return entity;
    }
}
