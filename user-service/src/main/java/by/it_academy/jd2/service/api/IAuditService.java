package by.it_academy.jd2.service.api;

import by.it_academy.jd2.dao.entity.UserEntity;
import org.example.mylib.tm.itacademy.dto.UserCheckDTO;

public interface IAuditService {
    UserCheckDTO meContextDetails(String token);

    void send(UserEntity entity, String text, String id);

    void send(UserEntity entity, String text);
}
