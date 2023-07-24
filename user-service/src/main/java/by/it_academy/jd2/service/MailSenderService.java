package by.it_academy.jd2.service;

import by.it_academy.jd2.service.api.IMailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService implements IMailSenderService {
    private final static Logger LOGGER = LoggerFactory.getLogger(MailSenderService.class);
    private final JavaMailSender mailSender;

    public MailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("vp@gmail.com");
            this.mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send mail", e);
            throw new IllegalStateException("failed to send email");
        }
    }
}
