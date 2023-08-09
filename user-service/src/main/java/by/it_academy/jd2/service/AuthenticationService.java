package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.TokenDTO;
import by.it_academy.jd2.core.dto.UserLoginDTO;
import by.it_academy.jd2.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.dao.entity.TokenEntity;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.api.*;
import by.it_academy.jd2.service.supportservices.authentification.JwtService;
import by.it_academy.jd2.service.supportservices.authentification.UserHolder;
import org.example.mylib.tm.itacademy.exceptions.EntityNotFoundException;
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
    private static final String DATA_FROM_CONTEXT_ERROR = "Data from context error, please try again after new user authorization!";
    private static final String USER_VERIFIED = "User verified";

    private final IUserService userService;
    private final IAuditService auditService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IConfirmationTokenService tokenService;
    private final IMailSenderService mailSenderService;
    private final UserHolder userHolder;


    public AuthenticationService(IUserService userService,
                                 IAuditService auditService, JwtService jwtService,
                                 AuthenticationManager authenticationManager,
                                 IConfirmationTokenService tokenService,
                                 IMailSenderService mailSenderService, UserHolder userHolder) {
        this.userService = userService;
        this.auditService = auditService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.mailSenderService = mailSenderService;
        this.userHolder = userHolder;
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
        TokenEntity confirmationToken = new TokenEntity(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                entity
        );

        this.tokenService.save(confirmationToken);
        this.mailSenderService.send(dto, token.toString());
    }

    @Transactional(readOnly = true)
    @Override
    public TokenDTO authentication(UserLoginDTO dto) {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getMail(),
                        dto.getPassword()
                )
        );

        UserEntity user = userService.findByMail(dto.getMail())
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
        String jwtToken = jwtService.generateToken(user);

        this.auditService.send(user, USER_AUTHENTICATION);

        return TokenDTO.builder()
                .token(jwtToken)
                .build();
    }

    @Transactional
    @Override
    public String confirmToken(UUID token, String mail) {
        TokenEntity confirmationToken = tokenService.findByToken(token);
        String confirmationMail = confirmationToken.getUserEntity().getMail();

        if (!mail.equals(confirmationMail)) {
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
        this.userService.activated(confirmationMail);

        return USER_VERIFIED;
    }

    @Transactional(readOnly = true)
    @Override
    public UserEntity meDetails() {
        return this.userService
                .findByMail(
                        this.userHolder
                                .getUser()
                                .getUsername())
                .orElseThrow(()
                        -> new EntityNotFoundException(DATA_FROM_CONTEXT_ERROR));
    }
}
