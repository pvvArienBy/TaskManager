package by.it_academy.jd2.service.api;

import by.it_academy.jd2.core.dto.UserCreateDTO;
import by.it_academy.jd2.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.dao.entity.UserEntity;
import jakarta.validation.Valid;

import java.util.Optional;

public interface IUserService extends ICRUDService<UserEntity, UserCreateDTO> {

    UserEntity save(@Valid UserRegistrationDTO item);

    Optional<UserEntity> findByMail(String mail);
}
