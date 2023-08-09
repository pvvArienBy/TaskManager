package by.it_academy.jd2.service.converters;

import by.it_academy.jd2.core.dto.ProjectRefDTO;
import by.it_academy.jd2.core.dto.TaskDTO;
import by.it_academy.jd2.core.dto.UserRefDTO;
import by.it_academy.jd2.dao.entity.TaskEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class TaskEntityToDtoConverter
        implements Converter<TaskEntity, TaskDTO> {

    /**
     * Converts a TaskEntity object to a TaskDTO object.
     *
     * @param item the TaskEntity object to convert
     * @return the converted TaskDTO object
     */
    @Override
    public TaskDTO convert(TaskEntity item) {
        TaskDTO dto = new TaskDTO();
        dto.setUuid(item.getUuid());
        dto.setDtCreate(item.getDtCreate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        dto.setDtUpdate(item.getDtUpdate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        dto.setProject(new ProjectRefDTO(item.getProject()));
        dto.setTitle(item.getTitle());
        dto.setDescription(item.getDescription());
        dto.setStatus(item.getStatus());
        dto.setImplementer(new UserRefDTO(item.getImplementer()));

        return dto;
    }
}
