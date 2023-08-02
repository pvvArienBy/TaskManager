package by.it_academy.jd2.controller;

import by.it_academy.jd2.core.dto.TokenDTO;
import by.it_academy.jd2.core.dto.UserCheckDTO;
import by.it_academy.jd2.core.dto.UserLoginDTO;
import by.it_academy.jd2.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.service.api.IAuditService;
import by.it_academy.jd2.service.api.IAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class AuthenticationController {
    private final IAuthenticationService authService;
    private final IAuditService auditService;

    public AuthenticationController(IAuthenticationService authService, IAuditService auditService) {
        this.authService = authService;
        this.auditService = auditService;
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
    public ResponseEntity<UserCheckDTO> me(@RequestHeader("Authorization") String authorizationHeader) {
        UserCheckDTO dto = this.auditService.meDetails(authorizationHeader);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/registration/confirm")
    public ResponseEntity<?> confirm(@RequestParam("token") String token) {
        return ResponseEntity.ok(this.authService.confirmToken(token));
    }
}
