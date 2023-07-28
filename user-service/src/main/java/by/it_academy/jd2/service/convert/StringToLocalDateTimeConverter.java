package by.it_academy.jd2.service.convert;

import by.it_academy.jd2.core.dto.ErrorDTO;
import by.it_academy.jd2.core.enums.ErrorType;
import by.it_academy.jd2.service.exceptions.NotCorrectValueException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
public class StringToLocalDateTimeConverter
        implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String source) {
        try {
            Instant instant = Instant.ofEpochMilli(Long.parseLong(source));
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        } catch (NumberFormatException e) {
            List<ErrorDTO> errorResponse = new ArrayList<>();
            errorResponse.add(new ErrorDTO(ErrorType.ERROR,"Дата не корректна. Должны быть цифры!"));
            throw new NotCorrectValueException(errorResponse);
        }
    }
}