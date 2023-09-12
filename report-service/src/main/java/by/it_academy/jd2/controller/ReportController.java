package by.it_academy.jd2.controller;

import by.it_academy.jd2.core.dto.ReportCreateDTO;
import by.it_academy.jd2.core.enums.EType;
import by.it_academy.jd2.service.api.IReportFileService;
import by.it_academy.jd2.service.api.IReportService;
import org.example.mylib.tm.itacademy.dto.PageDTO;
import org.example.mylib.tm.itacademy.utils.ClassNameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final IReportFileService fileService;

    private static final Logger logger = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

    public ReportController(IReportService reportService, ConversionService conversionService, IReportFileService fileService) {
        this.reportService = reportService;
        this.conversionService = conversionService;
        this.fileService = fileService;
    }

    @PostMapping("/{type}")
    public ResponseEntity<?> save(@PathVariable EType type, @RequestBody Map<String, Object> param) {

        logger.info("Request export, start save report");
        this.reportService.save(new ReportCreateDTO(type, param));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") int page,
                                     @RequestParam(required = false, defaultValue = "20") int size) {
        logger.info("Request export, findAll report");
        return ResponseEntity.status(HttpStatus.OK)
                .body(conversionService.convert(this.reportService.getAll(PageRequest.of(page, size)), PageDTO.class));
    }

    @GetMapping("/{uuid}/export")
    public RedirectView export(@PathVariable UUID uuid) {
        logger.info("Request export, Get {} export report ", uuid);
        return this.reportService.getUrlReport(uuid);
    }

    @RequestMapping(value = "/{uuid}/export", method = RequestMethod.HEAD)
    public ResponseEntity<?> availabilityCheck(@PathVariable UUID uuid) {
        logger.info("Request export, HEAD Check status {} report", uuid);
        if (this.fileService.checkFileInData(uuid))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
