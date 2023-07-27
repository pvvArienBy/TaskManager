package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.TokenDTO;
import by.it_academy.jd2.core.dto.UserLoginDTO;
import by.it_academy.jd2.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.api.IAuthenticationService;
import by.it_academy.jd2.service.api.IUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {
    private final IUserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationService(IUserService userService,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public TokenDTO registration(UserRegistrationDTO dto) {
        UserEntity entity = userService.save(dto);
        var jwtToken = jwtService.generateToken(entity);

        return TokenDTO.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public TokenDTO authentication(UserLoginDTO dto) {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getMail(),
                        dto.getPassword()
                )
        );

        var user = userService.findByMail(dto.getMail())
                .orElseThrow(() -> new UsernameNotFoundException("Объект не найден!"));
        var jwtToken = jwtService.generateToken(user);
        return TokenDTO.builder()
                .token(jwtToken)
                .build();
    }
}
