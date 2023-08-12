package by.it_academy.jd2.service;

import by.it_academy.jd2.service.api.IAuditService;
import by.it_academy.jd2.service.api.feign.IAuditClientService;
import by.it_academy.jd2.service.api.feign.IUserClientService;
import by.it_academy.jd2.service.supportservices.JwtService;
import by.it_academy.jd2.service.supportservices.UserHolder;
import org.example.mylib.tm.itacademy.dto.AuditCreateDTO;
import org.example.mylib.tm.itacademy.dto.UserCheckDTO;
import org.example.mylib.tm.itacademy.enums.EssenceType;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class AuditService implements IAuditService {
    private final IAuditClientService feignClientService;
    private final IUserClientService userClientService;
    private final JwtService jwtService;
    private final ConversionService conversionService;
    private final UserHolder userHolder;

    public AuditService(IAuditClientService feignClientService,
                        IUserClientService userClientService, JwtService jwtService,
                        ConversionService conversionService,
                        UserHolder userHolder) {

        this.feignClientService = feignClientService;
        this.userClientService = userClientService;
        this.jwtService = jwtService;
        this.conversionService = conversionService;
        this.userHolder = userHolder;
    }

    @Override
    public void send(String text, String id, EssenceType type) {
        UserCheckDTO userCheckDTO = this.conversionService.convert(
                this.userClientService.meDetails(
                        this.userHolder.getToken()).getBody(), UserCheckDTO.class);

        AuditCreateDTO dto = new AuditCreateDTO();

        dto.setText(text);
        dto.setId(id);
        dto.setType(type);
        dto.setUser(userCheckDTO);
        dto.setDtCreate(LocalDateTime
                .now()
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli());

        this.feignClientService.save(dto);
    }

    @Override
    public UserCheckDTO meContextDetails(String token) {
        UserCheckDTO dto = this.jwtService.meContextDetails(token);
        return dto;
    }
}
