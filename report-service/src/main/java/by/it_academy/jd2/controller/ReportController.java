package by.it_academy.jd2.controller;

import by.it_academy.jd2.core.dto.ReportCreateDTO;
import by.it_academy.jd2.core.enums.EType;
import by.it_academy.jd2.service.api.IReportService;
import org.example.mylib.tm.itacademy.dto.PageDTO;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final IReportService reportService;
    private final ConversionService conversionService;

    public ReportController(IReportService reportService, ConversionService conversionService) {
        this.reportService = reportService;
        this.conversionService = conversionService;
    }


    @PostMapping("/{type}")
    public ResponseEntity<?> save(@PathVariable EType type, @RequestBody Map<String, Object> param) {


        this.reportService.save(new ReportCreateDTO(type, param));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") int page,
                                     @RequestParam(required = false, defaultValue = "20") int size) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(conversionService.convert(this.reportService.getAll(PageRequest.of(page, size)), PageDTO.class));
    }

    @GetMapping("/{uuid}/export")
    public RedirectView  export(@PathVariable UUID uuid) {
        return this.reportService.getUrlReport(uuid);
    }
}
