package by.it_academy.jd2.service.converters;

import by.it_academy.jd2.dao.entity.UserEntity;
import org.example.mylib.tm.itacademy.dto.PageDTO;
import org.example.mylib.tm.itacademy.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class PageEntityToPageDtoConverter
        implements Converter<Page<UserEntity>, PageDTO<UserDTO>> {
    private final UserEntityToDtoConverter userConverter;

    public PageEntityToPageDtoConverter(UserEntityToDtoConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public PageDTO<UserDTO> convert(Page<UserEntity> page) {
        PageDTO<UserDTO> dto = new PageDTO<>();

        dto.setNumber(page.getNumber());
        dto.setSize(page.getSize());
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalElements(page.getTotalElements());
        dto.setFirst(page.isFirst());
        dto.setNumberOfElements(page.getNumberOfElements());
        dto.setLast(page.isLast());
        dto.setContent(page.getContent().stream().map(userConverter::convert).collect(Collectors.toList()));

        return dto;
    }
}
