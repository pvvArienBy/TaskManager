package by.it_academy.jd2.service.api;

import by.it_academy.jd2.core.dto.TokenDTO;
import by.it_academy.jd2.core.dto.UserLoginDTO;
import by.it_academy.jd2.core.dto.UserRegistrationDTO;

public interface IAuthenticationService {
    TokenDTO registration(UserRegistrationDTO dto);

    TokenDTO authentication(UserLoginDTO dto);

    String confirmToken(String token, String mail);
}