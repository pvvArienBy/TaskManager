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
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserDao userDao;

    public UserService(IUserDao userService) {
        this.userDao = userService;
    }

    @Override
    public List<UserEntity> findAll() {
        return userDao.findAll();
    }

    @Override
    public UserEntity findById(Long id) {
        Optional<UserEntity> userOptional = userDao.findById(id);
        return userOptional.orElse(null);
    }

    @Override
    public UserEntity save(UserCreateDTO item) {
        return userDao.save(UserConvertUtil.toEntity(item));
    }

    @Override
    public void delete(CoordinatesDTO coordinates) {
        Optional<UserEntity> userOptional = userDao.findById(coordinates.getId());
        UserEntity entity = userOptional.get();
        if (entity != null && entity.getUpdateDate() != null) {
            if (coordinates.getVersion() == entity.getUpdateDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) {
                userDao.delete(entity);
            } else throw new UpdateEntityException("Объект обновлён! Попробуйте ещё раз! ");
        }
        else throw new UpdateEntityException("Такого объекта не существует!");

    }

    @Override
    public UserEntity save(CoordinatesDTO coordinates, UserCreateDTO item) {
        Optional<UserEntity> userOptional = userDao.findById(coordinates.getId());
        UserEntity entity = userOptional.get();
        if (entity != null && entity.getUpdateDate() != null) {
            if (coordinates.getVersion() == entity.getUpdateDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) {
                UserEntity updEntity = UserConvertUtil.toEntity(item);
                updEntity.setId(entity.getId());
                updEntity.setUpdateDate(entity.getUpdateDate());
                return userDao.save(updEntity);
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
//        return userDao.filterByName(text);
        return null;
    }
}
