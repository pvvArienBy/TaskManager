package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.UserCreateDTO;
import by.it_academy.jd2.core.dto.UserDTO;
import by.it_academy.jd2.dao.api.IUserDao;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.api.IUserService;

import java.util.List;

public class UserService implements IUserService {

    private final IUserDao userService;

    public UserService(IUserDao userService) {
        this.userService = userService;
    }


    @Override
    public List<UserEntity> get() {
        return null;
    }

    @Override
    public UserEntity get(Long id) {
        return null;
    }

    @Override
    public UserEntity add(UserCreateDTO item) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void update(UserDTO coordinates, UserCreateDTO item) {

    }

    @Override
    public boolean checkChildren(Long id) {
        return false;
    }

    @Override
    public boolean validateCoordinatesParam(String id) {
        return false;
    }
}
