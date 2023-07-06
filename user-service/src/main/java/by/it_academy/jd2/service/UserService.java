package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.CoordinatesDTO;
import by.it_academy.jd2.core.dto.UserCreateDTO;
import by.it_academy.jd2.dao.api.IUserDao;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.api.IUserService;
import by.it_academy.jd2.service.exceptions.UpdateEntityException;
import by.it_academy.jd2.service.util.UserConvertUtil;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
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
    public void remove(CoordinatesDTO coordinates) {
        UserEntity entity = userDao.get(coordinates.getId());
        if (entity != null && entity.getUpdateDate() != null) {
            if (coordinates.getVersion() == entity.getUpdateDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) {
                userDao.remove(entity);
            } else throw new UpdateEntityException("Объект обновлён! Попробуйте ещё раз! ");
        }
        else throw new UpdateEntityException("Такого объекта не существует!");

    }

    @Override
    public void update(CoordinatesDTO coordinates, UserCreateDTO item) {
        UserEntity entity = userDao.get(coordinates.getId());
        if (entity != null && entity.getUpdateDate() != null) {
            if (coordinates.getVersion() == entity.getUpdateDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) {
                UserEntity updEntity = UserConvertUtil.toEntity(item);
                updEntity.setId(entity.getId());
                updEntity.setUpdateDate(entity.getUpdateDate());
                userDao.update(updEntity);
            } else throw new UpdateEntityException("Объект обновлён! Попробуйте ещё раз! ");
        }
        else throw new UpdateEntityException("Такого объекта не существует!");
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
