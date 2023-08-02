package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.AuditCreateDTO;
import by.it_academy.jd2.core.dto.UserCheckDTO;
import by.it_academy.jd2.service.api.IAuditService;
import by.it_academy.jd2.service.feignapi.IAuditFeignClientService;
import org.springframework.stereotype.Service;

@Service
public class AuditClientService implements IAuditService {
    private final IAuditFeignClientService auditClient;
    private final JwtService jwtService;

    public AuditClientService(IAuditFeignClientService auditClient, JwtService jwtService) {
        this.auditClient = auditClient;
        this.jwtService = jwtService;
    }

    @Override
    public UserCheckDTO meDetails(String token) {
        UserCheckDTO dto = this.jwtService.meDetails(token);
        return dto;
    }

    @Override
    public void send(AuditCreateDTO dto) {
        this.auditClient.save(dto);
    }
}
