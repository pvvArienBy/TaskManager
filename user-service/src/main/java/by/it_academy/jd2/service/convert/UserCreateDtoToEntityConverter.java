package by.it_academy.jd2.service.convert;

import by.it_academy.jd2.core.dto.UserCreateDTO;
import by.it_academy.jd2.dao.entity.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserCreateDtoToEntityConverter
        implements Converter<UserCreateDTO, UserEntity> {

    /**
     * Converts a UserCreateDTO object to a UserEntity object.
     *
     * @param dto the UserCreateDTO object to convert
     * @return the converted UserEntity object
     */
    @Override
    public UserEntity convert(UserCreateDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setUuid(UUID.randomUUID());
        entity.setMail(dto.getMail());
        entity.setFio(dto.getFio());
        entity.setRole(dto.getRole());
        entity.setStatus(dto.getStatus());
        entity.setPassword(dto.getPassword());

        return entity;
    }
}
