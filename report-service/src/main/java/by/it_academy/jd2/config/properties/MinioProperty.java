package by.it_academy.jd2.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioProperty {
    private String bucket;
    private String url;
    private String accessKey;
    private String secretKey;
}
