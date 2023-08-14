package by.it_academy.jd2.service.converters;

import by.it_academy.jd2.core.dto.ProjectCreateUpdateDTO;
import by.it_academy.jd2.core.dto.RefDTO;
import by.it_academy.jd2.dao.entity.ProjectEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProjectCreateUpdateDtoToEntityConverter
        implements Converter<ProjectCreateUpdateDTO, ProjectEntity> {
    /**
     * Converts a ProjectCreateUpdateDTO object to a ProjectEntity object.
     *
     * @param dto the ProjectCreateUpdateDTO object to convert
     * @return the converted ProjectEntity object
     */
    @Override
    public ProjectEntity convert(ProjectCreateUpdateDTO dto) {
        ProjectEntity entity = new ProjectEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setManager(dto.getManager().getUuid());
        entity.setStaff(dto.getStaff().stream().map(RefDTO::getUuid).collect(Collectors.toList()));
        entity.setStatus(dto.getStatus());

        return entity;
    }
}
