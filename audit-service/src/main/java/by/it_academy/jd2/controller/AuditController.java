package by.it_academy.jd2.controller;

import by.it_academy.jd2.core.dto.AuditDTO;
import by.it_academy.jd2.core.dto.PageDTO;
import by.it_academy.jd2.service.api.IAuditService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/audit")
public class AuditController {
    private final IAuditService auditService;
    private final ConversionService conversionService;

    public AuditController(IAuditService auditService, ConversionService conversionService) {
        this.auditService = auditService;
        this.conversionService = conversionService;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<AuditDTO> findById(@PathVariable UUID uuid) {
        AuditDTO auditDTO = conversionService.convert(this.auditService.findById(uuid), AuditDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(auditDTO);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") int page,
                                     @RequestParam(required = false, defaultValue = "20") int size) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(conversionService.convert(this.auditService.findAll(PageRequest.of(page, size)), PageDTO.class));
    }

}
