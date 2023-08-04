package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.AuditCreateDTO;
import by.it_academy.jd2.core.dto.UserCheckDTO;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.api.IAuditService;
import by.it_academy.jd2.service.feignapi.IAuditFeignClientService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class AuditService implements IAuditService {
    private final IAuditFeignClientService feignClientService;
    private final JwtService jwtService;
    private final ConversionService conversionService;


    public AuditService(IAuditFeignClientService feignClientService,
                        JwtService jwtService,
                        ConversionService conversionService) {

        this.feignClientService = feignClientService;
        this.jwtService = jwtService;
        this.conversionService = conversionService;
    }

    @Override
    public UserCheckDTO meContextDetails(String token) {
        UserCheckDTO dto = this.jwtService.meDetails(token);
        return dto;
    }

    @Override
    public void save(UserEntity entity, String text, String id) {
        AuditCreateDTO dto = this.conversionService.convert(entity, AuditCreateDTO.class);
        dto.setText(text);
        dto.setId(id);

        this.feignClientService.save(dto);
    }

    @Override
    public void save(UserEntity entity, String text) {
        AuditCreateDTO dto = this.conversionService.convert(entity, AuditCreateDTO.class);
        dto.setText(text);
        dto.setId(entity.getUuid().toString());

        this.feignClientService.save(dto);
    }
}
