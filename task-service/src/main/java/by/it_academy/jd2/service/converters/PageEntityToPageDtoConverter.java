package by.it_academy.jd2.service.converters;

import by.it_academy.jd2.core.dto.ProjectDTO;
import by.it_academy.jd2.core.dto.TaskDTO;
import by.it_academy.jd2.dao.entity.ProjectEntity;
import by.it_academy.jd2.dao.entity.TaskEntity;
import org.example.mylib.tm.itacademy.dto.PageDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class PageEntityToPageDtoConverter
        implements Converter<Page<?>, PageDTO<?>> {
    private final ProjectEntityToDtoConverter projectEntityToDtoConverter;
    private final TaskEntityToDtoConverter taskEntityToDtoConverter;

    public PageEntityToPageDtoConverter(ProjectEntityToDtoConverter projectEntityToDtoConverter, TaskEntityToDtoConverter taskEntityToDtoConverter) {
        this.projectEntityToDtoConverter = projectEntityToDtoConverter;
        this.taskEntityToDtoConverter = taskEntityToDtoConverter;
    }


    @Override
    public PageDTO<?> convert(Page<?> page) {
        if (page.getContent().get(0) instanceof TaskEntity) {
            @SuppressWarnings("unchecked")
            Page<TaskEntity> entityPage = (Page<TaskEntity>) page;
            PageDTO<TaskDTO> dto = new PageDTO<>();
            dto.setNumber(entityPage.getNumber());
            dto.setSize(entityPage.getSize());
            dto.setTotalPages(entityPage.getTotalPages());
            dto.setTotalElements(entityPage.getTotalElements());
            dto.setFirst(entityPage.isFirst());
            dto.setNumberOfElements(entityPage.getNumberOfElements());
            dto.setLast(entityPage.isLast());
            dto.setContent(entityPage.getContent().stream().map(taskEntityToDtoConverter::convert).collect(Collectors.toList()));

            return dto;

        } else if (page.getContent().get(0) instanceof ProjectEntity) {
            @SuppressWarnings("unchecked")
            Page<ProjectEntity> entityPage = (Page<ProjectEntity>) page;
            PageDTO<ProjectDTO> dto = new PageDTO<>();
            dto.setNumber(entityPage.getNumber());
            dto.setSize(entityPage.getSize());
            dto.setTotalPages(entityPage.getTotalPages());
            dto.setTotalElements(entityPage.getTotalElements());
            dto.setFirst(entityPage.isFirst());
            dto.setNumberOfElements(entityPage.getNumberOfElements());
            dto.setLast(entityPage.isLast());
            dto.setContent(entityPage.getContent().stream().map(projectEntityToDtoConverter::convert).collect(Collectors.toList()));

            return dto;
        }

        return null;
    }

}
