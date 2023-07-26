package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.UserCreateDTO;
import by.it_academy.jd2.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.dao.api.IUserDao;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.api.IUserService;
import by.it_academy.jd2.service.exceptions.EntityNotFoundException;
import by.it_academy.jd2.service.exceptions.UpdateEntityException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class UserService implements IUserService {
    private final IUserDao userDao;
    private final ConversionService conversionService;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    public UserService(IUserDao userService,
                       ConversionService conversionService,
                       PasswordEncoder passwordEncoder,
                       Validator validator) {
        this.userDao = userService;
        this.conversionService = conversionService;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
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
    public UserEntity save(UUID uuid, LocalDateTime version, UserCreateDTO item) {
        Optional<UserEntity> userOptional = userDao.findById(uuid);
        UserEntity entity = userOptional.orElseThrow(() -> new EntityNotFoundException("Такого объекта не существует!"));
        if (entity.getDtUpdate() != null) {
            if (version.equals(entity.getDtUpdate())) {
                UserEntity updEntity = conversionService.convert(item, UserEntity.class);
                if (updEntity == null) {
                    throw new IllegalArgumentException("Не удалось преобразовать объект UserCreateDTO в UserEntity");
                }
                updEntity.setUuid(entity.getUuid());
                updEntity.setDtCreate(entity.getDtCreate());
                updEntity.setDtUpdate(entity.getDtUpdate());

                return this.userDao.save(updEntity);

            } else throw new UpdateEntityException("Объект обновлён! Попробуйте ещё раз! ");
        } else throw new EntityNotFoundException("Такого объекта не существует!!!");
    }

    @Override
    @Validated
    public UserEntity save(@Valid UserRegistrationDTO item) {

//        Set<ConstraintViolation<UserRegistrationDTO>> violations = validator.validate(item);
//        if (!violations.isEmpty()) {
//            throw new ConstraintViolationException(violations);
//        }


        UserEntity entity = Objects.requireNonNull(conversionService.convert(item, UserEntity.class));
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));

//        Set<ConstraintViolation<ErrorDTO>> violations2 = validator.validate(entity);
//        if (!violations.isEmpty()) {
//            throw new ConstraintViolationException(violations2);
//        }

        try {
            this.userDao.save(entity);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause().getMessage().contains("duplicate key value violates unique constraint \"fio\"")) {
                throw new EntityNotFoundException("tessttt");
            } else throw new EntityNotFoundException("tessttt22");
        }
        return entity;
    }

    @Override
    public Optional<UserEntity> findByMail(String mail) {
        return this.userDao.findByMail(mail);
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
