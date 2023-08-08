package by.it_academy.jd2.service.api.feign;

import by.it_academy.jd2.core.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USERS", path = "/users")
public interface IUserClientService {
    @GetMapping("/me")
    ResponseEntity<UserDTO> meDetails(@RequestHeader("Authorization") String authorizationHeader);
}