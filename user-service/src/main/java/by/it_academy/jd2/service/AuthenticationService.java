package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.TokenDTO;
import by.it_academy.jd2.core.dto.UserLoginDTO;
import by.it_academy.jd2.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.dao.entity.ConfirmationTokenEntity;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.api.IAuthenticationService;
import by.it_academy.jd2.service.api.IConfirmationTokenService;
import by.it_academy.jd2.service.api.IUserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthenticationService implements IAuthenticationService {
    private static final String USER_NAME_NOT_FOUND_MSG = "user with email %s not found";
    private final IUserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailValidatorService emailValidatorService;
    private final ConversionService conversionService;
    private final IConfirmationTokenService tokenService;


    public AuthenticationService(IUserService userService,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager,
                                 EmailValidatorService emailValidatorService, ConversionService conversionService, IConfirmationTokenService tokenService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.emailValidatorService = emailValidatorService;
        this.conversionService = conversionService;
        this.tokenService = tokenService;
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
    public String register(UserRegistrationDTO dto) {
        boolean isValidMail = this.emailValidatorService.test(dto.getMail());

        if (!isValidMail) {
            throw new IllegalStateException("email not valid");
        }

        return signInUser(dto);
    }

    @Override
    public TokenDTO authentication(UserLoginDTO request) {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getMail(),
                        request.getPassword()
                )
        );

        var user = userService.findByMail(request.getMail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return TokenDTO.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService
                .findByMail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NAME_NOT_FOUND_MSG, username)));
    }

    public String signInUser(UserRegistrationDTO dto) {
        boolean userExist = this.userService
                .findByMail(dto.getMail()).isPresent();

        if (userExist) {
            throw new IllegalStateException("email already token");
        }
        //todo tut encode proishodit po gaidu potom save po entity
        UserEntity entity = userService.save(dto);

        String  token = UUID.randomUUID().toString();
        ConfirmationTokenEntity confirmationToken = new ConfirmationTokenEntity(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                entity
        );

        this.tokenService.save(confirmationToken);

        //TODO Send email
        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationTokenEntity confirmationToken = tokenService.findByToken(token);

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
        return "confirmed";
    }
}
