package by.it_academy.jd2.service.api;

public interface IMailSenderService {
    void send(String to, String email);
    boolean validation(String s);
}
