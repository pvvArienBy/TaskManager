package by.it_academy.jd2.service.converters;

import by.it_academy.jd2.dao.entity.AuditEntity;
import org.example.mylib.tm.itacademy.dto.AuditCreateDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuditCreateDtoToEntityConverter implements Converter<AuditCreateDTO, AuditEntity> {
    @Override
    public AuditEntity convert(AuditCreateDTO dto) {
        AuditEntity entity = new AuditEntity();

        entity.setUuid(UUID.randomUUID());
        entity.setUserUuid(dto.getUser().getUuid());
        entity.setUserMail(dto.getUser().getMail());
        entity.setUserFio(dto.getUser().getFio());
        entity.setUserRole(dto.getUser().getRole());
        entity.setText(dto.getText());
        entity.setType(dto.getType());
        entity.setId(dto.getId());

        return entity;
    }
}
