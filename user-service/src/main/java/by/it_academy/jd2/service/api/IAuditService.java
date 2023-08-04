package by.it_academy.jd2.service.api;

import by.it_academy.jd2.core.dto.UserCheckDTO;
import by.it_academy.jd2.dao.entity.UserEntity;

public interface IAuditService {
    UserCheckDTO meContextDetails(String token);

    void send(UserEntity entity, String text, String id);

    void send(UserEntity entity, String text);
}
