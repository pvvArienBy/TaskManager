package by.it_academy.jd2.service.api;

import by.it_academy.jd2.core.dto.TokenDTO;
import by.it_academy.jd2.core.dto.UserLoginDTO;
import by.it_academy.jd2.core.dto.UserRegistrationDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAuthenticationService extends UserDetailsService {
    TokenDTO registration(UserRegistrationDTO dto);
    TokenDTO authentication(UserLoginDTO dto);
    String register(UserRegistrationDTO dto);
    String confirmToken(String token);
}
