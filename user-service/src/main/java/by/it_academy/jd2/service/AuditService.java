package by.it_academy.jd2.service;

import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.api.IAuditService;
import by.it_academy.jd2.service.api.feign.IAuditClientService;
import by.it_academy.jd2.service.supportservices.authentification.JwtService;
import org.example.mylib.tm.itacademy.dto.AuditCreateDTO;
import org.example.mylib.tm.itacademy.dto.UserCheckDTO;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class AuditService implements IAuditService {
    private final IAuditClientService feignClientService;
    private final JwtService jwtService;
    private final ConversionService conversionService;


    public AuditService(IAuditClientService feignClientService,
                        JwtService jwtService,
                        ConversionService conversionService) {

        this.feignClientService = feignClientService;
        this.jwtService = jwtService;
        this.conversionService = conversionService;
    }

    @Override
    public UserCheckDTO meContextDetails(String token) {
        UserCheckDTO dto = this.jwtService.meContextDetails(token);
        return dto;
    }

    @Override
    public void send(UserEntity entity, String text, String id) {
        AuditCreateDTO dto = this.conversionService.convert(entity, AuditCreateDTO.class);
        dto.setText(text);
        dto.setId(id);

        this.feignClientService.save(dto);
    }

    @Override
    public void send(UserEntity entity, String text) {
        AuditCreateDTO dto = this.conversionService.convert(entity, AuditCreateDTO.class);
        dto.setText(text);
        dto.setId(entity.getUuid().toString());

        this.feignClientService.save(dto);
    }
}
