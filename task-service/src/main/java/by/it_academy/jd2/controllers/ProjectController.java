package by.it_academy.jd2.controllers;

import by.it_academy.jd2.core.dto.ProjectCreateUpdateDTO;
import by.it_academy.jd2.core.dto.ProjectDTO;
import by.it_academy.jd2.dao.entity.ProjectEntity;
import by.it_academy.jd2.service.api.IProjectService;
import org.example.mylib.tm.itacademy.dto.PageDTO;
import org.example.mylib.tm.itacademy.utils.ClassNameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final IProjectService projectService;
    private final ConversionService conversionService;

    private static final Logger logger = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

    public ProjectController(IProjectService projectService, ConversionService conversionService) {
        this.projectService = projectService;
        this.conversionService = conversionService;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProjectDTO> findById(@PathVariable UUID uuid) {
        ProjectDTO projectDTO = conversionService.convert(this.projectService.get(uuid), ProjectDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(projectDTO);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") int page,
                                     @RequestParam(required = false, defaultValue = "20") int size,
                                     @RequestParam(required = false, defaultValue = "false") Boolean archived) {
        logger.info("Request export, findAll project");

        return ResponseEntity.status(HttpStatus.OK)
                .body(conversionService.convert(this.projectService.getAll(PageRequest.of(page, size), archived), PageDTO.class));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ProjectCreateUpdateDTO dto) {
        logger.info("Request export, start save project {}", dto.getName());
        this.projectService.save(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<ProjectDTO> save(@PathVariable UUID uuid,
                                           @PathVariable("dt_update") LocalDateTime dtUpdate,
                                           @RequestBody ProjectCreateUpdateDTO dto) {
        logger.info("Request export, start update {} - project", uuid);
        ProjectEntity projectEntity = this.projectService.update(uuid, dtUpdate, dto);
        ProjectDTO projectDTO = conversionService.convert(projectEntity, ProjectDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(projectDTO);
    }
}
