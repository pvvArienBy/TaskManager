package by.it_academy.jd2.service;

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
import java.util.UUID;

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
    public UserEntity findById(UUID uuid) {
        Optional<UserEntity> userOptional = userDao.findById(uuid);
        return userOptional.orElse(null);
    }

    @Override
    public UserEntity save(UserCreateDTO item) {
        return userDao.save(UserConvertUtil.toEntity(item));
    }

    @Override
    public void delete(UUID uuid, Long version) {
        Optional<UserEntity> userOptional = userDao.findById(uuid);
        UserEntity entity = userOptional.get();
        if (entity != null && entity.getUpdateDate() != null) {
            if (version == entity.getUpdateDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) {
                userDao.delete(entity);
            } else throw new UpdateEntityException("Объект обновлён! Попробуйте ещё раз! ");
        }
        else throw new UpdateEntityException("Такого объекта не существует!");
    }

    @Override
    public UserEntity save(UUID uuid, Long version, UserCreateDTO item) {
        Optional<UserEntity> userOptional = userDao.findById(uuid);
        UserEntity entity = userOptional.get();
        if (entity != null && entity.getUpdateDate() != null) {
            if (version == entity.getUpdateDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) {
                UserEntity updEntity = UserConvertUtil.toEntity(item);
                updEntity.setUuid(entity.getUuid()););
                updEntity.setUpdateDate(entity.getUpdateDate());
                return userDao.save(updEntity);
            } else throw new UpdateEntityException("Объект обновлён! Попробуйте ещё раз! ");
        }
        else throw new UpdateEntityException("Такого объекта не существует!");
    }
}
