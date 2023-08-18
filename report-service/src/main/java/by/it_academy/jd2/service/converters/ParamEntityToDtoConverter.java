package by.it_academy.jd2.service.converters;

import by.it_academy.jd2.dao.entity.ParamEntity;
import org.example.mylib.tm.itacademy.dto.ParamDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ParamEntityToDtoConverter
        implements Converter<ParamEntity, ParamDTO> {

    /**
     * Converts a ParamEntity object to a ParamDTO object.
     *
     * @param item the ParamEntity object to convert
     * @return the converted ParamDTO object
     */

    @Override
    public ParamDTO convert(ParamEntity item) {
        ParamDTO dto = new ParamDTO();
        dto.setUser(item.getUserUuid());
        dto.setFrom(item.getFromDt());
        dto.setTo(item.getTooDt());

        return dto;
    }
}
