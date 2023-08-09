package by.it_academy.jd2.controller;


import by.it_academy.jd2.service.api.IAuditService;
import org.example.mylib.tm.itacademy.dto.AuditCreateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
public class AuditReceiverController {
    private final IAuditService auditService;

    public AuditReceiverController(IAuditService auditService) {
        this.auditService = auditService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody AuditCreateDTO dto) {
        this.auditService.save(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

