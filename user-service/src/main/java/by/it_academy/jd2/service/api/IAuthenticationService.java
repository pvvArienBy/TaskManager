package by.it_academy.jd2.service.api;

import by.it_academy.jd2.core.dto.TokenDTO;
import by.it_academy.jd2.core.dto.UserLoginDTO;
import by.it_academy.jd2.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.dao.entity.UserEntity;

import java.util.UUID;

public interface IAuthenticationService {
    void registration(UserRegistrationDTO dto);

    TokenDTO authentication(UserLoginDTO dto);

    String confirmToken(UUID token, String mail);

    UserEntity meDetails();
}