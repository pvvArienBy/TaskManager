package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.TokenDTO;
import by.it_academy.jd2.core.dto.UserLoginDTO;
import by.it_academy.jd2.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.dao.entity.ConfirmationTokenEntity;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.api.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthenticationService implements IAuthenticationService {
    private final IUserService userService;
    private final IAuditService auditService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IConfirmationTokenService tokenService;
    private final IMailSenderService mailSenderService;


    public AuthenticationService(IUserService userService,
                                 IAuditService auditService, JwtService jwtService,
                                 AuthenticationManager authenticationManager,
                                 IConfirmationTokenService tokenService,
                                 IMailSenderService mailSenderService) {
        this.userService = userService;
        this.auditService = auditService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.mailSenderService = mailSenderService;
    }

    @Override
    public TokenDTO registration(UserRegistrationDTO dto) {
        boolean userExist = this.userService
                .findByMail(dto.getMail()).isPresent();

        if (userExist) {
            throw new IllegalStateException("User with this username is already registered");
        }

        UserEntity entity = userService.save(dto);
        var jwtToken = jwtService.generateToken(entity);

        String token = UUID.randomUUID().toString();
        ConfirmationTokenEntity confirmationToken = new ConfirmationTokenEntity(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                entity
        );

        this.tokenService.save(confirmationToken);
        this.mailSenderService.send(dto, token);

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

        this.auditService.send(this.userService.formAudit(user, "Аутентификация пользователя"));

        return TokenDTO.builder()
                .token(jwtToken)
                .build();
    }

    @Transactional
    public String confirmToken(String token, String mail) {
        ConfirmationTokenEntity confirmationToken = tokenService.findByToken(token);

        if (!mail.equals(confirmationToken.getUserEntity().getMail())) {
            throw new IllegalStateException("Error validating data provided for verification!");
        }

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expireAt = confirmationToken.getExpiresAt();

        if (expireAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        this.tokenService.setConfirmedAt(token);
        this.userService.enableUser(
                confirmationToken.getUserEntity().getMail());
// TODO: 02.08.2023 need ref
        return "User verified";
    }
}
