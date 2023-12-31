package by.it_academy.jd2.service.converters;

import by.it_academy.jd2.dao.entity.UserEntity;
import org.example.mylib.tm.itacademy.dto.AuditCreateDTO;
import org.example.mylib.tm.itacademy.dto.UserCheckDTO;
import org.example.mylib.tm.itacademy.enums.EssenceType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class UserEntityToAuditCreateDtoConverter
        implements Converter<UserEntity, AuditCreateDTO> {

    /**
     * Converts a UserEntity object to a AuditCreateDTO object.
     *
     * @param item the UserEntity object to convert
     * @return the converted AuditCreateDTO object
     */
    @Override
    public AuditCreateDTO convert(UserEntity item) {
        UserCheckDTO dto = new UserCheckDTO();
        dto.setUuid(item.getUuid());
        dto.setMail(item.getMail());
        dto.setFio(item.getFio());
        dto.setRole(item.getRole());

        AuditCreateDTO auditDTO = new AuditCreateDTO();
        auditDTO.setUser(dto);
        auditDTO.setDtCreate(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        auditDTO.setType(EssenceType.USER);

        return auditDTO;
    }
}
