package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.UserCreateUpdateDTO;
import by.it_academy.jd2.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.dao.repositories.IUserDao;
import by.it_academy.jd2.service.api.IAuditService;
import by.it_academy.jd2.service.api.IUserService;
import by.it_academy.jd2.service.supportservices.authentification.UserHolder;
import jakarta.validation.Valid;
import org.example.mylib.tm.itacademy.enums.ERole;
import org.example.mylib.tm.itacademy.enums.EStatusUser;
import org.example.mylib.tm.itacademy.exceptions.EntityNotFoundException;
import org.example.mylib.tm.itacademy.exceptions.UpdateEntityException;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class UserService implements IUserService {
    private static final String USER_UPDATER = "Updating user data";
    private static final String USER_NOT_FOUND = "User is not found";
    private static final String NEW_USER_CREATED = "Creating a new user under a different user";
    private static final String USER_UPDATED = "User updated! Try again";
    private static final String NEW_USER_REGISTRATION = "New user registration";
    private static final String REQUESTED_DATA_UUID = "Requested user data by UUID";

    private final IUserDao userDao;
    private final ConversionService conversionService;
    private final IAuditService auditService;
    private final UserHolder userHolder;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserDao userService,
                       ConversionService conversionService,
                       IAuditService auditService,
                       UserHolder userHolder, PasswordEncoder passwordEncoder) {

        this.userDao = userService;
        this.conversionService = conversionService;
        this.auditService = auditService;
        this.userHolder = userHolder;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserEntity> getAll(PageRequest pageRequest) {
        return this.userDao.findAll(pageRequest);
    }

    @Transactional(readOnly = true)
    @Override
    public UserEntity get(UUID uuid) {
        UserEntity entity =  this.userDao
                .findById(uuid)
                .orElseThrow(()
                        -> new EntityNotFoundException(USER_NOT_FOUND));

        this.auditService.send(getMe(), REQUESTED_DATA_UUID, uuid.toString());

        return entity;
    }

    @Transactional
    @Override
    public UserEntity save(UserCreateUpdateDTO item) {
        item.setPassword(passwordEncoder.encode(item.getPassword()));
        UserEntity entity = Objects.requireNonNull(
                conversionService
                        .convert(item, UserEntity.class));
        entity.setUuid(UUID.randomUUID());

        this.userDao.saveAndFlush(entity);
        this.auditService.send(getMe(), NEW_USER_CREATED, entity.getUuid().toString());

        return entity;
    }

    @Transactional
    @Override
    public UserEntity save(@Valid UserRegistrationDTO item) {
        item.setPassword(passwordEncoder.encode(item.getPassword()));
        UserEntity entity = Objects.requireNonNull(
                conversionService
                        .convert(item, UserEntity.class));
        entity.setUuid(UUID.randomUUID());
        entity.setRole(ERole.USER);
        entity.setStatus(EStatusUser.WAITING_ACTIVATION);

        this.userDao.saveAndFlush(entity);
        this.auditService.send(entity, NEW_USER_REGISTRATION);

        return entity;
    }

    @Transactional
    @Override
    public UserEntity update(UUID uuid, LocalDateTime version, UserCreateUpdateDTO item) {
        UserEntity entity = userDao
                .findById(uuid)
                .orElseThrow(()
                        -> new EntityNotFoundException(USER_NOT_FOUND));

        if (!version.equals(entity.getDtUpdate())) {
            throw new UpdateEntityException(USER_UPDATED);
        }

        entity.setMail(item.getMail());
        entity.setFio(item.getFio());
        entity.setRole(item.getRole());
        entity.setStatus(item.getStatus());
        entity.setPassword(item.getPassword());

        UserEntity saveEntity = this.userDao.saveAndFlush(entity);

        this.auditService.send(getMe(), USER_UPDATER, entity.getUuid().toString());

        return saveEntity;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserEntity> findByMail(String mail) {
        return this.userDao.findByMail(mail);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByMail(String mail) {
        return this.userDao.existsByMail(mail);
    }

    @Transactional
    @Override
    public void activated(String mail) {
        UserEntity entity = userDao
                .findByMail(mail)
                .orElseThrow(()
                        -> new EntityNotFoundException(USER_NOT_FOUND));

        entity.setStatus(EStatusUser.ACTIVATED);
        this.userDao.save(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public UserEntity getMe() {
        return this.userDao
                .findByMail(this.userHolder
                        .getUser()
                        .getUsername())
                .orElseThrow(()
                        -> new EntityNotFoundException(USER_NOT_FOUND));
    }
}
