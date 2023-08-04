package by.it_academy.jd2.service.api;

import by.it_academy.jd2.core.dto.UserCheckDTO;
import by.it_academy.jd2.dao.entity.UserEntity;

public interface IAuditService {
    UserCheckDTO meDetails(String token);

    void save(UserEntity entity, String text, String id);

    void save(UserEntity entity, String text);
}
