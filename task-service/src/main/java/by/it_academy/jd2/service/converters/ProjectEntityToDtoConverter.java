package by.it_academy.jd2.service.converters;

import by.it_academy.jd2.core.dto.ProjectDTO;
import by.it_academy.jd2.core.dto.UserRefDTO;
import by.it_academy.jd2.dao.entity.ProjectEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.stream.Collectors;

@Component
public class ProjectEntityToDtoConverter
        implements Converter<ProjectEntity, ProjectDTO> {

    /**
     * Converts a ProjectEntity object to a ProjectDTO object.
     *
     * @param item the ProjectEntity object to convert
     * @return the converted ProjectDTO object
     */
    @Override
    public ProjectDTO convert(ProjectEntity item) {
        ProjectDTO dto = new ProjectDTO();
        dto.setUuid(item.getUuid());
        dto.setDtCreate(item.getDtCreate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        dto.setDtUpdate(item.getDtUpdate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setManager(new UserRefDTO(item.getManager()));
        dto.setStaff(item.getStaff().stream().map(UserRefDTO::new).collect(Collectors.toList()));
        dto.setStatus(item.getStatus());

        return dto;
    }
}
