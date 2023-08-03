package by.it_academy.jd2.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "mailset")
public class MailProperty {
    private String url;
    private String mailfrom;
    private String htmlform;
}
