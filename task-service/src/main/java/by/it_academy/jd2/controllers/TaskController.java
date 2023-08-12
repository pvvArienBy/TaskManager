package by.it_academy.jd2.controllers;

import by.it_academy.jd2.core.dto.TaskCreateUpdateDTO;
import by.it_academy.jd2.core.dto.TaskDTO;
import by.it_academy.jd2.core.enums.ETaskStatus;
import by.it_academy.jd2.dao.entity.TaskEntity;
import by.it_academy.jd2.service.api.ITaskService;
import org.example.mylib.tm.itacademy.dto.PageDTO;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final ITaskService taskService;
    private final ConversionService conversionService;

    public TaskController(ITaskService taskService, ConversionService conversionService) {
        this.taskService = taskService;
        this.conversionService = conversionService;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<TaskDTO> findById(@PathVariable UUID uuid) {
        TaskDTO taskDTO = conversionService.convert(this.taskService.get(uuid), TaskDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(taskDTO);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") int page,
                                     @RequestParam(required = false, defaultValue = "20") int size,
                                     @RequestParam(required = false, defaultValue = "") List<UUID> project,
                                     @RequestParam(required = false, defaultValue = "") List<UUID> implementer,
                                     @RequestParam(required = false, defaultValue = "") List<ETaskStatus> status) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(conversionService.convert(
                        this.taskService.getAll(
                                PageRequest.of(page, size), project,implementer, status), PageDTO.class));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody TaskCreateUpdateDTO dto) {
        this.taskService.save(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<TaskDTO> save(@PathVariable UUID uuid,
                                        @PathVariable("dt_update") LocalDateTime dtUpdate,
                                        @RequestBody TaskCreateUpdateDTO dto) {
        TaskEntity taskEntity = this.taskService.update(uuid, dtUpdate, dto);
        TaskDTO taskDTO = conversionService.convert(taskEntity, TaskDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(taskDTO);
    }

    @PatchMapping("/{uuid}/dt_update/{dt_update}/status/{status}")
    public ResponseEntity<?> save(@PathVariable UUID uuid,
                                  @PathVariable("dt_update") LocalDateTime dtUpdate,
                                  @PathVariable ETaskStatus status) {
        this.taskService.update(uuid, dtUpdate, status);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
