package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.AuditDTO;
import by.it_academy.jd2.openfeignclient.TestAuditClient;
import by.it_academy.jd2.service.api.IAuditService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuditClientService implements IAuditService {

    private final TestAuditClient auditClient;

    public AuditClientService(TestAuditClient auditClient) {
        this.auditClient = auditClient;
    }

    @Override
    public AuditDTO test(UUID id) {
        ResponseEntity<AuditDTO> auditResponseEntity = this.auditClient.findById(id);
        AuditDTO dto = auditResponseEntity.getBody();
        return dto;
    }
}
