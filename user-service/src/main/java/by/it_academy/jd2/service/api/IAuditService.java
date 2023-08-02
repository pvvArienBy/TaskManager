package by.it_academy.jd2.service.api;

import by.it_academy.jd2.core.dto.AuditCreateDTO;
import by.it_academy.jd2.core.dto.UserCheckDTO;

public interface IAuditService {
    UserCheckDTO meDetails(String token);

    void send(AuditCreateDTO dto);
}
