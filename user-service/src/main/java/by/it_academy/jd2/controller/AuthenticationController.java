package by.it_academy.jd2.controller;

import by.it_academy.jd2.core.dto.UserLoginDTO;
import by.it_academy.jd2.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.api.IAuthenticationService;
import jakarta.validation.Valid;
import org.example.mylib.tm.itacademy.dto.UserDTO;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class AuthenticationController {
    private final IAuthenticationService authService;
    private final ConversionService conversionService;

    public AuthenticationController(IAuthenticationService authService,
                                    ConversionService conversionService) {
        this.authService = authService;
        this.conversionService = conversionService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody UserRegistrationDTO dto) {
        this.authService.registration(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authentication(@RequestBody @Valid UserLoginDTO dto) {
        return ResponseEntity.ok(this.authService.authentication(dto));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> meDetails(@RequestHeader("Authorization") String authorizationHeader) {
        UserEntity entity = this.authService.meDetails();

        return ResponseEntity.status(HttpStatus.OK).body(conversionService.convert(entity, UserDTO.class));
    }

    @GetMapping("/verification")
    public ResponseEntity<?> confirm(@RequestParam UUID code, @RequestParam String mail) {
        return ResponseEntity.ok(this.authService.confirmToken(code, mail));
    }
}
