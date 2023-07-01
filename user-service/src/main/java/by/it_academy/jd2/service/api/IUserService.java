package by.it_academy.jd2.service.api;

import by.it_academy.jd2.core.dto.UserCreateDTO;
import by.it_academy.jd2.core.dto.UserDTO;
import by.it_academy.jd2.dao.entity.UserEntity;

public interface IUserService extends ICRUDService <UserEntity, UserCreateDTO, UserDTO>{

    boolean checkChildren (Long id);

    boolean validateCoordinatesParam(String id);
}
