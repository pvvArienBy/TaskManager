package by.it_academy.jd2.controller;

import by.it_academy.jd2.core.dto.AuditDTO;
import by.it_academy.jd2.dao.entity.Audit;
import by.it_academy.jd2.service.api.IAuditService;
import org.example.mylib.tm.itacademy.dto.ParamDTO;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/report")
public class AuditReportController {
    private final IAuditService auditService;
    private final ConversionService conversionService;

    public AuditReportController(IAuditService auditService, ConversionService conversionService) {
        this.auditService = auditService;
        this.conversionService = conversionService;
    }

    @PostMapping
    public ResponseEntity<List<AuditDTO>> getList(@RequestBody ParamDTO dto) {
        List<AuditDTO> dtos = new ArrayList<>();

        for (Audit entity : this.auditService.getList(dto)) {
            dtos.add(this.conversionService.convert((entity), AuditDTO.class));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(dtos);
    }
}
