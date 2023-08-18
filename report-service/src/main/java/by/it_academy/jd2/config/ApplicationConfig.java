package by.it_academy.jd2.config;

import by.it_academy.jd2.config.properties.MinioProperty;
import by.it_academy.jd2.service.api.feign.IUserClientService;
import by.it_academy.jd2.service.supportservices.MyUserDetailsService;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final MinioProperty minioProperty;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder().endpoint(
                minioProperty.getUrl())
                .credentials(minioProperty.getAccessKey(), minioProperty.getSecretKey())
                .build();
    }

    @Bean
    public MyUserDetailsService userDetailsService(IUserClientService IUserClientService) {
        return new MyUserDetailsService(IUserClientService);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(MyUserDetailsService myUserDetailService,
                                                         PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(myUserDetailService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
