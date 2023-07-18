package by.it_academy.jd2.service.api;

import by.it_academy.jd2.core.dto.UserRegistrationDTO;

public interface IAuthenticationService {

    void registration(UserRegistrationDTO dto);
}
