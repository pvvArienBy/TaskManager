package by.it_academy.jd2.controllers;

import by.it_academy.jd2.core.dto.CoordinatesDTO;
import by.it_academy.jd2.core.dto.UserCreateDTO;
import by.it_academy.jd2.core.dto.UserDTO;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.UserService;
import by.it_academy.jd2.service.api.IUserService;
import by.it_academy.jd2.service.util.UserConvertUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable Long id) {
        return UserConvertUtil.toDTO(userService.findById(id));
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
    public ResponseEntity<UserDTO> save(@RequestBody UserCreateDTO userCreateDTO, @RequestParam Long id, @RequestParam Long version) {
        CoordinatesDTO coordinatesDTO = new CoordinatesDTO(id,version);
        UserEntity userEntity = userService.save(coordinatesDTO, userCreateDTO);
        UserDTO userDTO = UserConvertUtil.toDTO(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> delete(@RequestParam Long id, @RequestParam Long version) {
        CoordinatesDTO coordinatesDTO = new CoordinatesDTO(id,version);
        userService.delete(coordinatesDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
