package by.it_academy.jd2.controller;

import by.it_academy.jd2.core.dto.*;
import by.it_academy.jd2.service.api.IAuditService;
import by.it_academy.jd2.service.api.IAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class AuthenticationController {
    private final IAuthenticationService authService;
    private final IAuditService auditService;

    public AuthenticationController(IAuthenticationService userAuthService, IAuditService auditService) {
        this.authService = userAuthService;
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

    @GetMapping("/test/{uuid}")
    public ResponseEntity<AuditDTO> findByIdAudit(@PathVariable UUID uuid) {
        AuditDTO userDTO = this.auditService.test(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }
}
