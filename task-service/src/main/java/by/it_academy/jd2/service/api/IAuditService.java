package by.it_academy.jd2.service.api;

import org.example.mylib.tm.itacademy.dto.UserCheckDTO;
import org.example.mylib.tm.itacademy.enums.EssenceType;

public interface IAuditService {
    UserCheckDTO meContextDetails(String token);

    void send(String text, String id, EssenceType type);
}
