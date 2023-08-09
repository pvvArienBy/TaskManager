package by.it_academy.jd2.service.utils;

import by.it_academy.jd2.core.dto.archtoDeelee.ProjectCreateDTO;
import by.it_academy.jd2.core.dto.ProjectDTO;
import by.it_academy.jd2.dao.entity.ProjectEntity;

import java.time.ZoneId;
import java.util.Objects;

public class ProjectConvertUtil {

    /**
     * Converts a ProjectEntity object to a ProjectDTO object.
     *
     * @param entity the ProjectEntity object to convert
     * @return the converted ProjectDTO object
     */
    public static ProjectDTO toDTO(ProjectEntity entity) {
        Objects.requireNonNull(entity, "The item parameter cannot be null");
        ProjectDTO dto = new ProjectDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setCreator(entity.getCreator());
        dto.setUsers(entity.getUsers());
        dto.setVersion(entity.getUpdateDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        return dto;
    }

    /**
     * Converts a ProjectCreateDTO object to a ProjectEntity object.
     *
     * @param dto the ProjectCreateDTO object to convert
     * @return the converted ProjectEntity object
     */
    public static ProjectEntity toEntity(ProjectCreateDTO dto) {
        Objects.requireNonNull(dto, "The dto parameter cannot be null");
        ProjectEntity entity = new ProjectEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCreator(dto.getCreator());
        entity.setUsers(dto.getUsers());

        return entity;
    }


    /**
     * Converts a ProjectDTO object to a ProjectEntity object.
     *
     * @param dto the ProjectDTO object to convert
     * @return the converted ProjectEntity object
     */
    public static ProjectEntity toEntity(ProjectDTO dto) {
        Objects.requireNonNull(dto, "The dto parameter cannot be null");
        ProjectEntity entity = new ProjectEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCreator(dto.getCreator());
        entity.setUsers(dto.getUsers());

        return entity;
    }
}
