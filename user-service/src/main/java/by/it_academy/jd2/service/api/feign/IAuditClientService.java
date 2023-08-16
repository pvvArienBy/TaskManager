package by.it_academy.jd2.service.api.feign;

import org.example.mylib.tm.itacademy.dto.AuditCreateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "AUDIT")
public interface IAuditClientService {
    @PostMapping("/internal")
    ResponseEntity<?> save(@RequestHeader("Authorization") String authorizationHeader,
                           @RequestBody AuditCreateDTO dto);
}
