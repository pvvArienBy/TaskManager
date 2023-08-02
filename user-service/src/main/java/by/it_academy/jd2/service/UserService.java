package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.AuditCreateDTO;
import by.it_academy.jd2.core.dto.UserCreateUpdateDTO;
import by.it_academy.jd2.core.dto.UserRegistrationDTO;
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
    private final UserHolder userHolder;
    private final IAuditService auditService;

    public UserService(IUserDao userService, ConversionService conversionService, UserHolder userHolder, IAuditService auditService) {
        this.userDao = userService;
        this.conversionService = conversionService;
        this.userHolder = userHolder;
        this.auditService = auditService;
    }

    @Override
    public Page<UserEntity> findAll(PageRequest pageRequest) {
        return this.userDao.findAll(pageRequest);
    }

    @Override
    public UserEntity findById(UUID uuid) {
        Optional<UserEntity> userOptional = this.userDao.findById(uuid);
        this.auditService.send(formAudit("Запрашивал данные пользователя по UUID", uuid.toString()));

        return userOptional.orElseThrow(() -> new EntityNotFoundException("Объект не найден!"));
    }

    @Override
    public UserEntity save(UserCreateUpdateDTO item) {
        UserEntity entity = this.userDao.save(
                Objects.requireNonNull(
                        conversionService.convert(item, UserEntity.class)));
        this.auditService.send(formAudit("Создание нового пользователя под другим пользователем", entity.getUuid().toString()));
        return entity;
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

                UserEntity saveEntity = this.userDao.save(updEntity);

                this.auditService.send(formAudit("Обновление данных пользователя", saveEntity.getUuid().toString()));

                return saveEntity;

            } else throw new UpdateEntityException("Объект обновлён! Попробуйте ещё раз! ");
        } else throw new EntityNotFoundException("Такого объекта не существует!!!");
    }

    @Override
    public UserEntity save(@Valid UserRegistrationDTO item) {

        UserEntity entity = this.userDao.save(
                Objects.requireNonNull(
                        conversionService.convert(item, UserEntity.class)));

        this.auditService.send(formAudit(entity,"Регистрация нового пользователя"));

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
    public AuditCreateDTO formAudit(String text, String id) {
        String username = this.userHolder.getUser().getUsername();

        UserEntity entity = this.userDao.findByMail(username)
                .orElseThrow(() -> new EntityNotFoundException("Такого объекта не существует!"));

        AuditCreateDTO dto = this.conversionService.convert(entity, AuditCreateDTO.class);
        dto.setText(text);
        dto.setId(id);

        return dto;
    }

    @Override
    public AuditCreateDTO formAudit(UserEntity entity, String text) {
        AuditCreateDTO dto = this.conversionService.convert(entity, AuditCreateDTO.class);
        assert dto != null;
        dto.setText(text);
        dto.setId(entity.getUuid().toString());

        return dto;
    }
}
