package by.it_academy.jd2.controller;

import by.it_academy.jd2.core.dto.*;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.UserHolder;
import by.it_academy.jd2.service.api.IAuditService;
import by.it_academy.jd2.service.api.IAuthenticationService;
import by.it_academy.jd2.service.api.IUserService;
import by.it_academy.jd2.service.exceptions.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class AuthenticationController {
    private final IAuthenticationService authService;
    private final IUserService userService;
    private final ConversionService conversionService;
    private final UserHolder userHolder;

    public AuthenticationController(IAuthenticationService authService,
                                    IUserService userService,
                                    ConversionService conversionService,
                                    UserHolder userHolder) {
        this.authService = authService;
        this.userService = userService;
        this.conversionService = conversionService;
        this.userHolder = userHolder;
    }

    @PostMapping("/registration")
    public ResponseEntity<TokenDTO> registration(@RequestBody UserRegistrationDTO dto) {
        return ResponseEntity.ok(this.authService.registration(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> authentication(@RequestBody @Valid UserLoginDTO dto) {
        return ResponseEntity.ok(this.authService.authentication(dto));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> details() {

        String username  =this.userHolder.getUser()
                .getUsername();
        Optional<UserEntity> item = this.userService
                .findByMail(username);
        UserEntity entity = item.orElseThrow(() -> new EntityNotFoundException(
                "Ошибка данных из контекста, попробуйте еще раз после новой авторизации пользователя!"));

        return ResponseEntity.status(HttpStatus.OK).body(conversionService.convert(entity, UserDTO.class));
    }

    @GetMapping("/registration/confirm")
    public ResponseEntity<?> confirm(@RequestParam("token") String token) {
        return ResponseEntity.ok(this.authService.confirmToken(token));
    }
}
