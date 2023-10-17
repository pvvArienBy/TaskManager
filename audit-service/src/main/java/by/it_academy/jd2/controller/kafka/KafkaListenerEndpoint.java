package by.it_academy.jd2.controller.kafka;

import by.it_academy.jd2.service.api.IAuditService;
import org.example.mylib.tm.itacademy.dto.AuditCreateDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class KafkaListenerEndpoint {
    private IAuditService auditService;

    public KafkaListenerEndpoint(IAuditService auditService) {
        this.auditService = auditService;
    }

    @KafkaListener(topics = "audit_info")
    public void listener(AuditCreateDTO auditCreateDTO) {
        auditService.save(auditCreateDTO);
    }

}
