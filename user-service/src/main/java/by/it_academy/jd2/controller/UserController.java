package by.it_academy.jd2.controller;

import by.it_academy.jd2.core.dto.PageDTO;
import by.it_academy.jd2.core.dto.UserCreateUpdateDTO;
import by.it_academy.jd2.core.dto.UserDTO;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.api.IUserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final IUserService userService;

    private final ConversionService conversionService;

    public UserController(IUserService userService, ConversionService conversionService) {
        this.userService = userService;
        this.conversionService = conversionService;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserDTO> findById(@PathVariable UUID uuid) {
        UserDTO userDTO = conversionService.convert(this.userService.findById(uuid), UserDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @GetMapping
    public PageDTO<UserDTO> findAll(@RequestParam(required = false, defaultValue = "0") int page,
                                    @RequestParam(required = false, defaultValue = "20") int size) {
        return conversionService.convert(this.userService.findAll(PageRequest.of(page, size)), PageDTO.class);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserCreateUpdateDTO dto) {
        this.userService.save(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<UserDTO> save(@PathVariable UUID uuid, @PathVariable LocalDateTime dt_update, @RequestBody UserCreateUpdateDTO dto) {
        UserEntity userEntity = this.userService.save(uuid, dt_update, dto);
        UserDTO userDTO = conversionService.convert(userEntity, UserDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }
}
