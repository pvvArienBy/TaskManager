package by.it_academy.jd2.service.api.feign;

import org.example.mylib.tm.itacademy.dto.ResultUsersVerificationDTO;
import org.example.mylib.tm.itacademy.dto.UserDTO;
import org.example.mylib.tm.itacademy.dto.UsersVerificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USERS")
public interface IUserClientService {
    @GetMapping("/users/me")
    ResponseEntity<UserDTO> meDetails(@RequestHeader("Authorization") String authorizationHeader);

    @PostMapping("/internal")
    ResponseEntity<ResultUsersVerificationDTO> checkForProject(@RequestHeader("Authorization") String authorizationHeader, @RequestBody UsersVerificationDTO dto);
}