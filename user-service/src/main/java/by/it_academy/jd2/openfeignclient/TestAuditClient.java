package by.it_academy.jd2.openfeignclient;

import by.it_academy.jd2.core.dto.AuditDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "AUDIT")
public interface TestAuditClient {

    @GetMapping("/audit/{uuid}")
    ResponseEntity<AuditDTO> findById(@PathVariable UUID uuid);
}
