package by.it_academy.jd2.controller;

import by.it_academy.jd2.core.dto.ResultUsersVerificationDTO;
import by.it_academy.jd2.core.dto.UserCreateUpdateDTO;
import by.it_academy.jd2.core.dto.UsersVerificationDTO;
import by.it_academy.jd2.service.api.IUserVerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal")
public class UserVerificationController {
    private final IUserVerificationService verificationService;

    public UserVerificationController(IUserVerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @PostMapping
    public ResponseEntity<ResultUsersVerificationDTO> checkForProject(@RequestHeader("Authorization") String authorizationHeader, @RequestBody UsersVerificationDTO dto) {
        ResultUsersVerificationDTO resultDTO = this.verificationService.check(dto);

        return ResponseEntity.status(HttpStatus.OK).body(resultDTO);
    }
}
