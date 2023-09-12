package by.it_academy.jd2;

import by.it_academy.jd2.config.properties.JWTProperty;
import by.it_academy.jd2.config.properties.MinioProperty;
import jakarta.annotation.PreDestroy;
import org.example.mylib.tm.itacademy.utils.ClassNameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@EnableDiscoveryClient
@EnableFeignClients
@EnableJpaRepositories
@EnableTransactionManagement
@EnableConfigurationProperties({JWTProperty.class, MinioProperty.class})
@SpringBootApplication
public class ReportServiceApplication {
    private static final Logger logger = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

    public static void main(String[] args) {
        logger.info("Start microservice: {} - [REPORT]", ClassNameUtil.getCurrentClassName());
        SpringApplication.run(ReportServiceApplication.class, args);
    }

    @PreDestroy
    public void onExit() {
        logger.info("Stopping the microservice {} - [REPORT]", ClassNameUtil.getCurrentClassName());
    }
}