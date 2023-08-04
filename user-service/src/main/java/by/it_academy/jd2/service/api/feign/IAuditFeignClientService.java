package by.it_academy.jd2.service.api.feign;

import by.it_academy.jd2.core.dto.AuditCreateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "AUDIT")
public interface IAuditFeignClientService {
    @PostMapping("/internal")
    ResponseEntity<?> save(@RequestBody AuditCreateDTO dto);
}
