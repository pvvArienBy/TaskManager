package by.it_academy.jd2.service.util;

import by.it_academy.jd2.core.dto.UserCreateDTO;
import by.it_academy.jd2.core.dto.UserDTO;
import by.it_academy.jd2.dao.entity.UserEntity;

import java.time.ZoneId;
import java.util.Objects;

public class UserConvertUtil  {

    /**
     * Converts a UserEntity object to a UserDTO object.
     *
     * @param item the UserEntity object to convert
     * @return the converted UserDTO object
     */
    public static UserDTO toDTO(UserEntity item) {
        Objects.requireNonNull(item, "The item parameter cannot be null");
        UserDTO dto = new UserDTO();
        dto.setId(item.getId());
        dto.setFirstName(item.getFirstName());
        dto.setLastName(item.getLastName());
        dto.setMail(item.getMail());
        dto.setTelegram(item.getTelegram());
        dto.setPosition(item.getPosition());
        dto.setRole(item.getRole());
        dto.setStatus(item.getStatus());
        dto.setVersion(item.getUpdateDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        return dto;
    }

    /**
     * Converts a UserDTO object to a UserEntity object.
     *
     * @param dto the UserDTO object to convert
     * @return the converted UserEntity object
     */
    public static UserEntity toEntity(UserCreateDTO dto) {
        Objects.requireNonNull(dto, "The dto parameter cannot be null");
        UserEntity entity = new UserEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getFirstName());
        entity.setMail(dto.getMail());
        entity.setPassword(dto.getPassword());
        entity.setTelegram(dto.getTelegram());
        entity.setPosition(dto.getPosition());
        entity.setRole(dto.getRole());
        entity.setStatus(dto.getStatus().toString());
        return entity;
    }
}
