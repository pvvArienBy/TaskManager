package by.it_academy.jd2.service.api;

import by.it_academy.jd2.core.dto.UserRegistrationDTO;

public interface IMailSenderService {
    void send(UserRegistrationDTO dto, String link);
    boolean validation(String s);
}
