package by.it_academy.jd2.service.api;

import by.it_academy.jd2.core.dto.UserCreateUpdateDTO;
import by.it_academy.jd2.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.dao.entity.UserEntity;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface IUserService extends ICRUDService<UserEntity, UserCreateUpdateDTO> {
    UserEntity save(@Valid UserRegistrationDTO item);

    Optional<UserEntity> findByMail(String mail);

    boolean existsByMail(String mail);

    Page<UserEntity> findAll(PageRequest of);

}
