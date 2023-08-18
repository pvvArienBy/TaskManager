package by.it_academy.jd2.service.converters;

import by.it_academy.jd2.core.dto.ReportDTO;
import by.it_academy.jd2.dao.entity.ReportEntity;
import org.example.mylib.tm.itacademy.dto.PageDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class PageEntityToPageDtoConverter
        implements Converter<Page<ReportEntity>, PageDTO<ReportDTO>> {
    private final ReportEntityToDtoConverter reportEntityToDtoConverter;

    public PageEntityToPageDtoConverter(ReportEntityToDtoConverter userConverter) {
        this.reportEntityToDtoConverter = userConverter;
    }

    @Override
    public PageDTO<ReportDTO> convert(Page<ReportEntity> page) {
        PageDTO<ReportDTO> dto = new PageDTO<>();

        dto.setNumber(page.getNumber());
        dto.setSize(page.getSize());
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalElements(page.getTotalElements());
        dto.setFirst(page.isFirst());
        dto.setNumberOfElements(page.getNumberOfElements());
        dto.setLast(page.isLast());
        dto.setContent(page.getContent().stream().map(reportEntityToDtoConverter::convert).collect(Collectors.toList()));

        return dto;
    }
}
