package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.UserCreateDTO;
import by.it_academy.jd2.core.dto.UserDTO;
import by.it_academy.jd2.dao.api.IUserDao;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.api.IUserService;
import by.it_academy.jd2.service.util.UserConvertUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final IUserDao userDao;

    public UserService(IUserDao userService) {
        this.userDao = userService;
    }

    @Override
    public List<UserEntity> get() {
        return userDao.get();
    }

    @Override
    public UserEntity get(Long id) {
        return userDao.get(id);
    }

    @Override
    public UserEntity add(UserCreateDTO item) {
        return userDao.add(UserConvertUtil.toEntity(item));
    }

    @Override
    public void remove(Long id) {
    userDao.remove(id);
    }

    @Override
    public void update(UserDTO coordinates, UserCreateDTO item) {

    }

    @Override
    public boolean validateCoordinatesParam(String id) {
        return id != null && id.matches("\\d+") && Long.parseLong(id) > 0;
    }


    @Override
    public List<UserEntity> filterByName(String text) {
        return userDao.filterByName(text);
    }
}
