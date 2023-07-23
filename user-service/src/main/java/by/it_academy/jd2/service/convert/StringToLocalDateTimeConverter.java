package by.it_academy.jd2.service.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;

@Component
public class StringToLocalDateTimeConverter
        implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String source) {
        Instant instant = Instant.ofEpochMilli(Long.parseLong(source));
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}