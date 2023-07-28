package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.UserCreateUpdateDTO;
import by.it_academy.jd2.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.dao.api.IUserDao;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.api.IUserService;
import by.it_academy.jd2.service.exceptions.EntityNotFoundException;
import by.it_academy.jd2.service.exceptions.UpdateEntityException;
import jakarta.validation.Valid;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class UserService implements IUserService {
    private final IUserDao userDao;
    private final ConversionService conversionService;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserDao userService,
                       ConversionService conversionService,
                       PasswordEncoder passwordEncoder) {
        this.userDao = userService;
        this.conversionService = conversionService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<UserEntity> findAll(PageRequest pageRequest) {
        return this.userDao.findAll(pageRequest);
    }

    @Override
    public UserEntity findById(UUID uuid) {
        Optional<UserEntity> userOptional = this.userDao.findById(uuid);

        return userOptional.orElseThrow(() -> new EntityNotFoundException("Объект не найден!"));
    }

    @Override
    public UserEntity save(UserCreateUpdateDTO item) {
        return this.userDao.save(Objects.requireNonNull(conversionService.convert(item, UserEntity.class)));
    }

    @Override
    public UserEntity save(UUID uuid, LocalDateTime version, UserCreateUpdateDTO item) {
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
    public UserEntity save(@Valid UserRegistrationDTO item) {

        UserEntity entity = Objects.requireNonNull(conversionService.convert(item, UserEntity.class));
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        this.userDao.save(entity);

        return entity;
    }

    @Override
    public Optional<UserEntity> findByMail(String mail) {
        return this.userDao.findByMail(mail);
    }

    @Override
    public boolean existsByMail(String mail) {
        return this.userDao.existsByMail(mail);
    }
}
