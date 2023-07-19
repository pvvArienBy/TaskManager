package by.it_academy.jd2.service.convert;

import by.it_academy.jd2.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.core.enums.ERole;
import by.it_academy.jd2.core.enums.EStatusUser;
import by.it_academy.jd2.dao.entity.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserRegistrationDtoToEntityConverter implements Converter<UserRegistrationDTO, UserEntity> {

    /**
     * Converts a UserCreateDTO object to a UserEntity object.
     *
     * @param dto the UserCreateDTO object to convert
     * @return the converted UserEntity object
     */
    @Override
    public UserEntity convert(UserRegistrationDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setUuid(UUID.randomUUID());
        entity.setMail(dto.getMail());
        entity.setFio(dto.getFio());
        entity.setRole(ERole.USER);
        entity.setStatus(EStatusUser.WAITING_ACTIVATION);
        entity.setPassword(dto.getPassword());

        return entity;
    }
}
