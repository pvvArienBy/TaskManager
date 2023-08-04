package by.it_academy.jd2.service.converters;

import by.it_academy.jd2.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.core.enums.ERole;
import by.it_academy.jd2.core.enums.EStatusUser;
import by.it_academy.jd2.dao.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserRegistrationDtoToEntityConverter implements Converter<UserRegistrationDTO, UserEntity> {
    private final PasswordEncoder passwordEncoder;

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
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));

        return entity;
    }
}
