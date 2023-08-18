package by.it_academy.jd2.service.converters;

import by.it_academy.jd2.dao.entity.ParamEntity;
import org.example.mylib.tm.itacademy.dto.ParamDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class ParamDTOToEntityConverter
        implements Converter<ParamDTO, ParamEntity> {

    /**
     * Converts a ParamAuditDTO object to a ParamEntity object.
     *
     * @param dto the ParamAuditDTO object to convert
     * @return the converted ParamEntity object
     */
    @Override
    public ParamEntity convert(ParamDTO dto) {
        ParamEntity entity = new ParamEntity();
        entity.setUuid(UUID.randomUUID());
        entity.setUserUuid(dto.getUser());
        entity.setFromDt(dto.getFrom());
        entity.setTooDt(dto.getTo());

        return entity;
    }
}