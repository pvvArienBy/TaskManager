package by.it_academy.jd2.controllers;

import by.it_academy.jd2.core.dto.UserCreateDTO;
import by.it_academy.jd2.core.dto.UserDTO;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.api.IUserService;
import by.it_academy.jd2.service.util.UserConvertUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{uuid}")
    public UserDTO findById(@PathVariable UUID uuid) {
        return UserConvertUtil.toDTO(userService.findById(uuid));
    }

    @GetMapping("/")
    public List<UserDTO> findAll() {
        return UserConvertUtil.toDTO(userService.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<UserDTO> save(@RequestBody UserCreateDTO userCreateDTO) {
        UserEntity userEntity = userService.save(userCreateDTO);
        UserDTO userDTO = UserConvertUtil.toDTO(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @PutMapping("/")
    public ResponseEntity<UserDTO> save(@RequestBody UserCreateDTO userCreateDTO, @RequestParam UUID uuid, @RequestParam Long version) {
        UserEntity userEntity = userService.save(uuid, version, userCreateDTO);
        UserDTO userDTO = UserConvertUtil.toDTO(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> delete(@RequestParam UUID uuid, @RequestParam Long version) {
        userService.delete(uuid, version);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
