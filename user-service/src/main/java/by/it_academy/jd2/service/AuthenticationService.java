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
    private static final String USER_NOT_FOUND = "User is not found";
    private static final String USERNAME_ALREADY_REGISTERED = "User with this username is already registered";
    private static final String USER_AUTHENTICATION = "User authentication";
    private static final String VALIDATING_VERIFICATION_DATA = "Problem validating data provided for verification!";
    private static final String EMAIL_ALREADY_CONFIRMED = "email already confirmed";
    private static final String TOKEN_EXPIRED = "token expired";
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

    @Transactional
    @Override
    public void registration(UserRegistrationDTO dto) {
        boolean userExist = this.userService
                .findByMail(dto.getMail()).isPresent();

        if (userExist) {
            throw new IllegalStateException(USERNAME_ALREADY_REGISTERED);
        }

        UserEntity entity = userService.save(dto);

        UUID token = UUID.randomUUID();
        ConfirmationTokenEntity confirmationToken = new ConfirmationTokenEntity(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                entity
        );

        this.tokenService.save(confirmationToken);
        this.mailSenderService.send(dto, token.toString());
    }

    @Transactional
    @Override
    public TokenDTO authentication(UserLoginDTO dto) {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getMail(),
                        dto.getPassword()
                )
        );

        var user = userService.findByMail(dto.getMail())
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
        var jwtToken = jwtService.generateToken(user);

        this.auditService.save(user, USER_AUTHENTICATION);

        return TokenDTO.builder()
                .token(jwtToken)
                .build();
    }

    @Transactional
    @Override
    public String confirmToken(UUID token, String mail) {
        ConfirmationTokenEntity confirmationToken = tokenService.findByToken(token);

        if (!mail.equals(confirmationToken.getUserEntity().getMail())) {
            throw new IllegalStateException(VALIDATING_VERIFICATION_DATA);
        }

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException(EMAIL_ALREADY_CONFIRMED);
        }

        LocalDateTime expireAt = confirmationToken.getExpiresAt();

        if (expireAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException(TOKEN_EXPIRED);
        }

        this.tokenService.setConfirmedAt(token);
        this.userService.activated(
                confirmationToken.getUserEntity().getMail());
// TODO: 02.08.2023 need ref
        return "User verified";
    }
}
