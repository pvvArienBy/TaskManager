package by.it_academy.jd2;

import by.it_academy.jd2.config.properties.JWTProperty;
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
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDiscoveryClient
@EnableFeignClients
@EnableJpaRepositories
@EnableTransactionManagement
@EnableConfigurationProperties({JWTProperty.class})
@SpringBootApplication
public class TaskServiceApplication {
	private static final Logger logger = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

	public static void main(String[] args) {
		logger.info("Start microservice: {} - [TASK]", ClassNameUtil.getCurrentClassName());
		SpringApplication.run(TaskServiceApplication.class, args);
	}

	@PreDestroy
	public void onExit() {
		logger.info("Stopping the microservice {} - [TASK]", ClassNameUtil.getCurrentClassName());
	}
}
