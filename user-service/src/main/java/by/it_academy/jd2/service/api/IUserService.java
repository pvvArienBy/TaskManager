package by.it_academy.jd2.service.api;

import by.it_academy.jd2.core.dto.UserCreateDTO;
import by.it_academy.jd2.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.dao.entity.UserEntity;

public interface IUserService extends ICRUDService <UserEntity, UserCreateDTO>{

    UserEntity save(UserRegistrationDTO item);
}
