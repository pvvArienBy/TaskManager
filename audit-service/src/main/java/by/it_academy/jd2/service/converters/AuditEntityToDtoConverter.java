package by.it_academy.jd2.service.converters;

import by.it_academy.jd2.core.dto.AuditDTO;
import by.it_academy.jd2.dao.entity.Audit;
import org.example.mylib.tm.itacademy.dto.UserCheckDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.UUID;

@Component
public class AuditEntityToDtoConverter implements Converter<Audit, AuditDTO> {
    @Override
    public AuditDTO convert(Audit item) {
        AuditDTO dto = new AuditDTO();
        UserCheckDTO userDTO = new UserCheckDTO();

        userDTO.setUuid(item.getUserUuid());
        userDTO.setMail(item.getUserMail());
        userDTO.setFio(item.getUserFio());
        userDTO.setRole(item.getUserRole());
        dto.setUuid(UUID.fromString(item.getUuid()));
        dto.setDtCreate(item.getDtCreate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        dto.setUser(userDTO);
        dto.setText(item.getText());
        dto.setType(item.getType());
        dto.setId(item.getId());

        return dto;
    }
}
