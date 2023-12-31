package by.it_academy.jd2.service;

import by.it_academy.jd2.config.properties.MailProperty;
import by.it_academy.jd2.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.service.api.IMailSenderService;
import by.it_academy.jd2.service.api.IThymeleafService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.example.mylib.tm.itacademy.exceptions.CustomValidationException;
import org.example.mylib.tm.itacademy.exceptions.ValidationListException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailSenderService implements IMailSenderService {
    private static final String FAILED_TO_SEND_EMAIL = "failed to send email";
    private static final String SMTP_SERVER_ERROR = "Invalid mail address. No SMTP server connection.";

    private final JavaMailSender mailSender;
    private final IThymeleafService thymeleafService;
    private final MailProperty property;


    public MailSenderService(JavaMailSender mailSender,
                             IThymeleafService thymeleafService,
                             MailProperty mailProperty) {
        this.mailSender = mailSender;
        this.thymeleafService = thymeleafService;
        this.property = mailProperty;
    }

    @Async
    @Override
    public void send(UserRegistrationDTO dto, String token) {
        try {
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            helper.setTo(dto.getMail());

            Map<String, Object> variables = new HashMap<>();
            variables.put("mail", dto.getMail());
            variables.put("token", token);
            variables.put("url", property.getUrl());
            helper.setText(thymeleafService.createContent(property.getHtmlform(), variables), true);
            helper.setFrom(property.getMailfrom());
            helper.setSubject(property.getSubject());

            try {
                this.mailSender.send(mimeMessage);
            } catch (MailException e) {
                throw new MailSendException(SMTP_SERVER_ERROR);
            }

        } catch (MessagingException e) {
            throw new IllegalStateException(FAILED_TO_SEND_EMAIL);
        }
    }
}
