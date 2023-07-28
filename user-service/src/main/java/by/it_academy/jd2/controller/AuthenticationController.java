package by.it_academy.jd2.controller;

import by.it_academy.jd2.core.dto.TokenDTO;
import by.it_academy.jd2.core.dto.UserLoginDTO;
import by.it_academy.jd2.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.service.api.IAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class AuthenticationController {

    private final IAuthenticationService authService;

    public AuthenticationController(IAuthenticationService userAuthService) {
        this.authService = userAuthService;
    }

    @PostMapping("/registration")
    public ResponseEntity<TokenDTO> registration(@RequestBody UserRegistrationDTO dto) {
        return ResponseEntity.ok(this.authService.registration(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> authentication(@RequestBody @Valid UserLoginDTO dto) {
        return ResponseEntity.ok(this.authService.authentication(dto));
    }
}
