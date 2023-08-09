package by.it_academy.jd2.service.converters;

import by.it_academy.jd2.core.dto.AuditDTO;
import by.it_academy.jd2.dao.entity.AuditEntity;
import org.example.mylib.tm.itacademy.dto.PageDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class PageEntityToPageDtoConverter
        implements Converter<Page<AuditEntity>, PageDTO<AuditDTO>> {

    private final AuditEntityToDtoConverter auditEntityToDtoConverter;

    public PageEntityToPageDtoConverter(AuditEntityToDtoConverter auditEntityToDtoConverter) {
        this.auditEntityToDtoConverter = auditEntityToDtoConverter;
    }

    @Override
    public PageDTO<AuditDTO> convert(Page<AuditEntity> page) {
        PageDTO<AuditDTO> dto = new PageDTO<>();

        dto.setNumber(page.getNumber());
        dto.setSize(page.getSize());
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalElements(page.getTotalElements());
        dto.setFirst(page.isFirst());
        dto.setNumberOfElements(page.getNumberOfElements());
        dto.setLast(page.isLast());
        dto.setContent(page.getContent().stream().map(auditEntityToDtoConverter::convert).collect(Collectors.toList()));

        return dto;
    }
}
