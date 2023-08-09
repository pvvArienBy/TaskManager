package by.it_academy.jd2.service.utils;

import by.it_academy.jd2.core.dto.archtoDeelee.FileDTO;
import by.it_academy.jd2.dao.entity.FileEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileConvertUtil {

    /**
     * Converts a FileEntity object to a FileDTO object.
     *
     * @param entity the FileEntity object to convert
     * @return the converted FileDTO object
     */
    public static FileDTO toDTO(FileEntity entity) {
        Objects.requireNonNull(entity, "The item parameter cannot be null");
        FileDTO dto = new FileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPath(entity.getPath());
        dto.setContentType(entity.getContentType());

        return dto;
    }

    /**
     * Converts a FileDTO object to a FileEntity object.
     *
     * @param dto the FileDTO object to convert
     * @return the converted FileEntity object
     */
    public static FileEntity toEntity(FileDTO dto) {
        Objects.requireNonNull(dto, "The dto parameter cannot be null");
        FileEntity entity = new FileEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPath(dto.getPath());
        entity.setContentType(dto.getContentType());

        return entity;
    }

    public static List<FileDTO> toDTO(List<FileEntity> entityList) {
        List<FileDTO> dtoList = new ArrayList<>();

        for (FileEntity fileEntity : entityList) {
            FileDTO dto = new FileDTO();
            dto.setId(fileEntity.getId());
            dto.setName(fileEntity.getName());
            dto.setPath(fileEntity.getPath());
            dto.setContentType(fileEntity.getContentType());
        }

        return dtoList;
    }

    public static List<FileEntity> toEntity(List<FileDTO> dtoList) {
        List<FileEntity> entityList = new ArrayList<>();

        for (FileDTO dto : dtoList) {
            FileEntity entity = new FileEntity();
            entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setPath(dto.getPath());
            entity.setContentType(dto.getContentType());
            entityList.add(entity);
        }

        return entityList;
    }
}
