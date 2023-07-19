package by.it_academy.jd2.controller;

import by.it_academy.jd2.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.service.api.IAuthenticationService;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class AuthenticationController {

    private final IAuthenticationService userAuthService;

    private final ConversionService conversionService;

    public AuthenticationController(IAuthenticationService userAuthService, ConversionService conversionService) {
        this.userAuthService = userAuthService;
        this.conversionService = conversionService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> save(@RequestBody UserRegistrationDTO dto) {
        this.userAuthService.registration(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @GetMapping("/verification")
//    public ResponseEntity<UserDTO> verification(@RequestParam String code, @RequestParam String email) {
//
//        UserDTO userDTO = conversionService.convert(userEntity, UserDTO.class);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
//        return null;
//    }
}
