package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.UserCreateDTO;
import by.it_academy.jd2.dao.api.IUserDao;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.api.IUserService;
import by.it_academy.jd2.service.exceptions.UpdateEntityException;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private final IUserDao userDao;
    private final ConversionService conversionService;

    public UserService(IUserDao userService, ConversionService conversionService) {
        this.userDao = userService;
        this.conversionService = conversionService;
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
        return userDao.save(Objects.requireNonNull(conversionService.convert(item, UserEntity.class)));
    }

    @Override
    public void delete(UUID uuid, Long version) {
        Optional<UserEntity> userOptional = userDao.findById(uuid);
        UserEntity entity = userOptional.get();
        if (entity.getDtUpdate() != null) {
            if (version == entity.getDtUpdate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) {
                userDao.delete(entity);
            } else throw new UpdateEntityException("Объект обновлён! Попробуйте ещё раз! ");
        }
        else throw new UpdateEntityException("Такого объекта не существует!");
    }

    @Override
    public UserEntity save(UUID uuid, Long version, UserCreateDTO item) {
        Optional<UserEntity> userOptional = userDao.findById(uuid);
        UserEntity entity = userOptional.get();
        if (entity.getDtUpdate() != null) {
            if (version == entity.getDtUpdate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) {
                UserEntity updEntity = conversionService.convert(item, UserEntity.class);
                assert updEntity != null;
                updEntity.setUuid(entity.getUuid());
                updEntity.setDtCreate(entity.getDtCreate());
                updEntity.setDtUpdate(entity.getDtUpdate());
                return userDao.save(updEntity);
            } else throw new UpdateEntityException("Объект обновлён! Попробуйте ещё раз! ");
        }
        else throw new UpdateEntityException("Такого объекта не существует!");
    }
}
