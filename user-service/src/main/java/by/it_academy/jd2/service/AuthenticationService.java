package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.service.api.IAuthenticationService;
import by.it_academy.jd2.service.api.IUserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {

    private final IUserService userService;

    private final ConversionService conversionService;

    public AuthenticationService(IUserService userService, ConversionService conversionService) {
        this.userService = userService;
        this.conversionService = conversionService;
    }

    @Override
    public void registration(UserRegistrationDTO dto) {
        this.userService.save(dto);
    }
}
