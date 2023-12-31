package by.it_academy.jd2.controller;

import by.it_academy.jd2.core.dto.*;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.api.IUserService;
import jakarta.validation.Valid;
import org.example.mylib.tm.itacademy.dto.PageDTO;
import org.example.mylib.tm.itacademy.dto.UserDTO;
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
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;
    private final ConversionService conversionService;

    private static final Logger logger = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

    public UserController(IUserService userService,
                          ConversionService conversionService) {
        this.userService = userService;
        this.conversionService = conversionService;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserDTO> findById(@PathVariable UUID uuid) {
        UserDTO userDTO = conversionService.convert(this.userService.get(uuid), UserDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") int page,
                                     @RequestParam(required = false, defaultValue = "20") int size) {
        logger.info("Request export, findAll project");
        return ResponseEntity.status(HttpStatus.OK)
                .body(conversionService.convert(this.userService.getAll(PageRequest.of(page, size)), PageDTO.class));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserCreateUpdateDTO dto) {
        logger.info("Request export, start save user {}", dto.getMail());
        this.userService.save(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<UserDTO> save(@PathVariable UUID uuid,
                                        @PathVariable("dt_update") LocalDateTime dtUpdate,
                                        @RequestBody @Valid UserCreateUpdateDTO dto) {
        logger.info("Request export, start update {} - user", uuid);
        UserEntity userEntity = this.userService.update(uuid, dtUpdate, dto);
        UserDTO userDTO = conversionService.convert(userEntity, UserDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }
}
