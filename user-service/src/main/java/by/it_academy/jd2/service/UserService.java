package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.UserCreateDTO;
import by.it_academy.jd2.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.dao.api.IUserDao;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.api.IUserService;
import by.it_academy.jd2.service.exceptions.EntityNotFoundException;
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
        return this.userDao.findAll();
    }

    @Override
    public UserEntity findById(UUID uuid) {
        Optional<UserEntity> userOptional = this.userDao.findById(uuid);
        return userOptional.orElseThrow(() -> new EntityNotFoundException("Не найдено"));
    }

    @Override
    public UserEntity save(UserCreateDTO item) {
        return this.userDao.save(Objects.requireNonNull(conversionService.convert(item, UserEntity.class)));
    }

    @Override
    public UserEntity save(UUID uuid, Long version, UserCreateDTO item) {
        Optional<UserEntity> userOptional = userDao.findById(uuid);
        UserEntity entity = userOptional.orElseThrow(() -> new EntityNotFoundException("Такого объекта не существует!"));
        if (entity.getDtUpdate() != null) {
            if (version == entity.getDtUpdate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) {
                UserEntity updEntity = conversionService.convert(item, UserEntity.class);
                if (updEntity == null) {
                    throw new IllegalArgumentException("Не удалось преобразовать объект UserCreateDTO в UserEntity");
                }
                updEntity.setUuid(entity.getUuid());
                updEntity.setDtCreate(entity.getDtCreate());
                updEntity.setDtUpdate(entity.getDtUpdate());
                return this.userDao.save(updEntity);
            } else throw new UpdateEntityException("Объект обновлён! Попробуйте ещё раз! ");
        }
        else throw new EntityNotFoundException("Такого объекта не существует!");
    }

    @Override
    public UserEntity save(UserRegistrationDTO item) {
        return this.userDao.save(Objects.requireNonNull(conversionService.convert(item, UserEntity.class)));
    }

    //   todo требует корректировки по аналогии с UserEntity save(UUID uuid, Long version, UserCreateDTO item)
//    @Override
//    public void delete(UUID uuid, Long version) {
//        Optional<UserEntity> userOptional = userDao.findById(uuid);
//        UserEntity entity = userOptional.get();
//        if (entity.getDtUpdate() != null) {
//            if (version == entity.getDtUpdate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) {
//                userDao.delete(entity);
//            } else throw new UpdateEntityException("Объект обновлён! Попробуйте ещё раз! ");
//        }
//        else throw new UpdateEntityException("Такого объекта не существует!");
//    }
}
