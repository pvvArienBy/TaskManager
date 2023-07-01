package by.it_academy.jd2.service.util;

import by.it_academy.jd2.core.dto.UserCreateDTO;
import by.it_academy.jd2.core.dto.UserDTO;
import by.it_academy.jd2.dao.entity.UserEntity;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

public class UserConvertUtil  {

    /**
     * Converts a UserEntity object to a UserDTO object.
     *
     * @param item the UserEntity object to convert
     * @return the converted UserDTO object
     * todo need test and debug test
     */
    public static UserDTO toDTO(UserEntity item) {
        Objects.requireNonNull(item, "The item parameter cannot be null");
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(dto, item);
        return dto;
    }

    /**
     * Converts a UserDTO object to a UserEntity object.
     *
     * @param dto the UserDTO object to convert
     * @return the converted UserEntity object
     * todo need test and debug test
     */
    public static UserEntity toEntity(UserCreateDTO dto) {
        Objects.requireNonNull(dto, "The dto parameter cannot be null");
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(entity, dto);
        return entity;
    }
}


//todo check after

//    public static UserDTO toDTO(UserEntity item) {
//        UserDTO dto = new UserDTO();
//        dto.setId(item.getId());
//        dto.setFirstName(item.getFirstName());
//        dto.setLastName(item.getLastName());
//        dto.setMail(item.getMail());
//        dto.setTelegram(item.getTelegram());
//        dto.setPosition(item.getPosition());
//        dto.setRole(item.getRole());
//        dto.setStatus(item.getStatus());
//        return dto;
//    }

//    private UserEntity toEntity(UserCreateDTO item) {
//        return new UserEntity(
//                item.getFirstName(),
//                item.getLastName(),
//                item.getMail(),
//                item.getPassword(),
//                item.getTelegram(),
//                item.getPosition(),
//                item.getRole(),
//                item.getStatus(),
//                item.getNotificationWay());
//    }
