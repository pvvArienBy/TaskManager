package by.it_academy.jd2.service.converters;

import by.it_academy.jd2.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.dao.entity.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationDtoToEntityConverter
        implements Converter<UserRegistrationDTO, UserEntity> {
    /**
     * Converts a UserCreateDTO object to a UserEntity object.
     *
     * @param dto the UserCreateDTO object to convert
     * @return the converted UserEntity object
     */
    @Override
    public UserEntity convert(UserRegistrationDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setMail(dto.getMail());
        entity.setFio(dto.getFio());
        entity.setPassword(dto.getPassword());

        return entity;
    }
}
