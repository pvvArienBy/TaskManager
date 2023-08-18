package by.it_academy.jd2.service.converters;

import by.it_academy.jd2.core.dto.ReportDTO;
import by.it_academy.jd2.dao.entity.ParamEntity;
import by.it_academy.jd2.dao.entity.ReportEntity;
import org.example.mylib.tm.itacademy.dto.ParamDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class ReportEntityToDtoConverter
        implements Converter<ReportEntity, ReportDTO> {

    /**
     * Converts a ProjectEntity object to a ProjectDTO object.
     *
     * @param item the ProjectEntity object to convert
     * @return the converted ProjectDTO object
     */
    @Override
    public ReportDTO convert(ReportEntity item) {
        ReportDTO dto = new ReportDTO();
        dto.setUuid(item.getUuid());
        dto.setDtCreate(item.getDtCreate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        dto.setDtUpdate(item.getDtUpdate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        dto.setStatus(item.getStatus());
        dto.setType(item.getType());
        dto.setDescription(item.getDescription());
        dto.setParams(converterParam(item.getParamEntity()));

        return dto;
    }

    private ParamDTO converterParam(ParamEntity entity) {
        ParamDTO dto = new ParamDTO();
        dto.setUser(entity.getUserUuid());
        dto.setFrom(entity.getFromDt());
        dto.setTo(entity.getTooDt());
        return dto;
    }
}
