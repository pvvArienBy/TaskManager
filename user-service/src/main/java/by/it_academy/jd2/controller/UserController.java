package by.it_academy.jd2.controller;

import by.it_academy.jd2.core.dto.UserCreateDTO;
import by.it_academy.jd2.core.dto.UserDTO;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.api.IUserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    public List<UserDTO> findAll() {
        List<UserDTO> dtoList = new ArrayList<>();
        for (UserEntity userEntity : this.userService.findAll()) {
            dtoList.add(conversionService.convert(userEntity, UserDTO.class));
        }

        return dtoList;
    }


    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserCreateDTO dto) {
        this.userService.save(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<UserDTO> save(@PathVariable UUID uuid,@PathVariable LocalDateTime dt_update, @RequestBody UserCreateDTO dto) {
        UserEntity userEntity = this.userService.save(uuid, dt_update, dto);
        UserDTO userDTO = conversionService.convert(userEntity, UserDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

//    @DeleteMapping("/")
//    public ResponseEntity<Void> delete(@RequestParam UUID uuid, @RequestParam Long version) {
//        userService.delete(uuid, version);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }
}
