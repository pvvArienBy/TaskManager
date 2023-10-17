package by.it_academy.jd2.service;

import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.api.IAuditService;
import by.it_academy.jd2.service.api.kafka.IAuditSenderKafkaClient;
import by.it_academy.jd2.service.supportservices.authentification.JwtService;
import org.example.mylib.tm.itacademy.dto.AuditCreateDTO;
import org.example.mylib.tm.itacademy.dto.UserCheckDTO;
import org.example.mylib.tm.itacademy.utils.ClassNameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class AuditService implements IAuditService {
    private final JwtService jwtService;
    private final ConversionService conversionService;
    private final IAuditSenderKafkaClient<String, Object> auditSenderKafkaClient;

    private static final Logger logger = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

    public AuditService(JwtService jwtService,
                        ConversionService conversionService,
                        IAuditSenderKafkaClient<String, Object> auditSenderKafkaClient) {

        this.jwtService = jwtService;
        this.conversionService = conversionService;
        this.auditSenderKafkaClient = auditSenderKafkaClient;
    }

    @Override
    public UserCheckDTO meContextDetails(String token) {
        UserCheckDTO dto = this.jwtService.meContextDetails(token);
        return dto;
    }

    @Override
    public void send(UserEntity entity, String text, String id) {
        AuditCreateDTO dto = this.conversionService.convert(entity, AuditCreateDTO.class);
        assert dto != null;
        dto.setText(text);
        dto.setId(id);

        try {
            this.auditSenderKafkaClient.send("audit_info", dto);
        } catch (Exception e) {
            logger.info("Send audit: {} - [USER]", ClassNameUtil.getCurrentClassName());
            e.printStackTrace();
        }

    }

    @Override
    public void send(UserEntity entity, String text) {
        AuditCreateDTO dto = this.conversionService.convert(entity, AuditCreateDTO.class);
        assert dto != null;
        dto.setText(text);
        dto.setId(entity.getUuid().toString());

        try {
            this.auditSenderKafkaClient.send("audit_info", dto);
        } catch (Exception e) {
            logger.info("Send audit: {} - [USER]", ClassNameUtil.getCurrentClassName());
            e.printStackTrace();
        }
    }

}
