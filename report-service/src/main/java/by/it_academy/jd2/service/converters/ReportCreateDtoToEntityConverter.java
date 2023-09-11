package by.it_academy.jd2.service.converters;

import by.it_academy.jd2.core.dto.ReportCreateDTO;
import by.it_academy.jd2.core.enums.EReportStatus;
import by.it_academy.jd2.dao.entity.ParamEntity;
import by.it_academy.jd2.dao.entity.ReportEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

@Component
public class ReportCreateDtoToEntityConverter
        implements Converter<ReportCreateDTO, ReportEntity> {
    /**
     * Converts a ReportCreateDTO object to a ReportEntity object.
     *
     * @param dto the ReportCreateDTO object to convert
     * @return the converted ReportEntity object
     */
    @Override
    public ReportEntity convert(ReportCreateDTO dto) {
        ReportEntity entity = new ReportEntity();

        entity.setStatus(EReportStatus.LOADED);
        entity.setType(dto.getType());
        entity.setParamEntity(getParam(dto.getParams()));
        entity.setDescription(getDescription(entity));

        return entity;
    }

    private ParamEntity getParam(Map<String, Object> params) {
        ParamEntity paramEntity = new ParamEntity();
        UUID uuid = UUID.fromString((String) params.get("user"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate from = LocalDate.parse((String) params.get("from"), formatter);
        LocalDate to = LocalDate.parse((String) params.get("to"), formatter);

        paramEntity.setUuid(UUID.randomUUID());
        paramEntity.setUserUuid(uuid);
        paramEntity.setFromDt(from);
        paramEntity.setTooDt(to.plusDays(1));
        return paramEntity;
    }

    private String getDescription(ReportEntity entity) {

        return String.format("Audit log for: %s - %s  by User - %s",
                entity.getParamEntity().getFromDt(),
                entity.getParamEntity().getTooDt(),
                entity.getParamEntity().getUserUuid());
    }

}
