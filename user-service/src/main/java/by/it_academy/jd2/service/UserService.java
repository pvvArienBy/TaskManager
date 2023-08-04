package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.UserCreateUpdateDTO;
import by.it_academy.jd2.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.core.enums.EStatusUser;
import by.it_academy.jd2.dao.api.IUserDao;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.api.IAuditService;
import by.it_academy.jd2.service.api.IUserService;
import by.it_academy.jd2.service.exceptions.EntityNotFoundException;
import by.it_academy.jd2.service.exceptions.UpdateEntityException;
import jakarta.validation.Valid;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public UserService(IUserDao userService,
                       ConversionService conversionService,
                       IAuditService auditService,
                       UserHolder userHolder) {

        this.userDao = userService;
        this.conversionService = conversionService;
        this.auditService = auditService;
        this.userHolder = userHolder;
    }

    @Override
    public Page<UserEntity> getAll(PageRequest pageRequest) {
        return this.userDao.findAll(pageRequest);
    }

    @Transactional
    @Override
    public UserEntity get(UUID uuid) {
        Optional<UserEntity> userOptional = this.userDao.findById(uuid);
        UserEntity entity = userOptional
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        String username = this.userHolder.getUser().getUsername();
        UserEntity editor = this.userDao.findByMail(username)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        this.auditService.save(editor, REQUESTED_DATA_UUID, uuid.toString());

        return entity;
    }

    @Transactional
    @Override
    public UserEntity save(UserCreateUpdateDTO item) {
        UserEntity entity = this.userDao.save(
                Objects.requireNonNull(
                        conversionService.convert(item, UserEntity.class)));

        String username = this.userHolder.getUser().getUsername();
        UserEntity editor = this.userDao.findByMail(username)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        this.auditService.save(editor, NEW_USER_CREATED, entity.getUuid().toString());

        return entity;
    }

    @Transactional
    @Override
    public UserEntity update(UUID uuid, LocalDateTime version, UserCreateUpdateDTO item) {
        Optional<UserEntity> userOptional = userDao.findById(uuid);
        UserEntity entity = userOptional.orElseThrow(() -> new EntityNotFoundException(
                USER_NOT_FOUND));

        if (!version.equals(entity.getDtUpdate())) {
            throw new UpdateEntityException(USER_UPDATED);
        }

        UserEntity updEntity = conversionService.convert(item, UserEntity.class);

        updEntity.setUuid(entity.getUuid());
        updEntity.setDtCreate(entity.getDtCreate());
        updEntity.setDtUpdate(entity.getDtUpdate());

        UserEntity saveEntity = this.userDao.save(updEntity);

        String username = this.userHolder.getUser().getUsername();
        UserEntity editor = this.userDao.findByMail(username)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        this.auditService.save(editor, USER_UPDATER, entity.getUuid().toString());

        return saveEntity;
    }

    @Transactional
    @Override
    public UserEntity save(@Valid UserRegistrationDTO item) {

        UserEntity entity = this.userDao.save(
                Objects.requireNonNull(
                        conversionService.convert(item, UserEntity.class)));

        this.auditService.save(entity, NEW_USER_REGISTRATION);

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


    @Override
    public void activated(String mail) {
        Optional<UserEntity> userOptional = userDao.findByMail(mail);
        UserEntity entity = userOptional
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        entity.setStatus(EStatusUser.ACTIVATED);
        this.userDao.save(entity);
    }
}
