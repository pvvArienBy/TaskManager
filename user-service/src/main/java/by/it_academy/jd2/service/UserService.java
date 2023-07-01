package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.UserCreateDTO;
import by.it_academy.jd2.core.dto.UserDTO;
import by.it_academy.jd2.dao.api.IUserDao;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.api.IUserService;
import by.it_academy.jd2.service.util.UserConvertUtil;

import java.util.List;

public class UserService implements IUserService {

    private final IUserDao userService;

    public UserService(IUserDao userService) {
        this.userService = userService;
    }

    @Override
    public List<UserEntity> get() {
        return userService.get();
    }

    @Override
    public UserEntity get(Long id) {
        return userService.get(id);
    }

    @Override
    public UserEntity add(UserCreateDTO item) {
        return userService.add(UserConvertUtil.toEntity(item));
    }

    @Override
    public void remove(Long id) {
    userService.remove(id);
    }

    @Override
    public void update(UserDTO coordinates, UserCreateDTO item) {

    }

    @Override
    public boolean validateCoordinatesParam(String id) {
        return id != null && id.matches("\\d+") && Long.parseLong(id) > 0;
    }
}
