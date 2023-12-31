package by.it_academy.jd2.service.converters;

import by.it_academy.jd2.core.dto.UserCreateUpdateDTO;
import by.it_academy.jd2.dao.entity.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserCreateUpdateDtoToEntityConverter
        implements Converter<UserCreateUpdateDTO, UserEntity> {

    /**
     * Converts a UserCreateUpdateDTO object to a UserEntity object.
     *
     * @param dto the UserCreateUpdateDTO object to convert
     * @return the converted UserEntity object
     */
    @Override
    public UserEntity convert(UserCreateUpdateDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setMail(dto.getMail());
        entity.setFio(dto.getFio());
        entity.setRole(dto.getRole());
        entity.setStatus(dto.getStatus());
        entity.setPassword(dto.getPassword());

        return entity;
    }
}
