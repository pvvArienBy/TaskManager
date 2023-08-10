package by.it_academy.jd2.service.converters;

import by.it_academy.jd2.core.dto.TaskCreateUpdateDTO;
import by.it_academy.jd2.dao.entity.TaskEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskCreateUpdateDtoToEntityConverter
        implements Converter<TaskCreateUpdateDTO, TaskEntity> {
    /**
     * Converts a TaskCreateUpdateDTO object to a TaskEntity object.
     *
     * @param dto the TaskCreateUpdateDTO object to convert
     * @return the converted TaskEntity object
     */
    @Override
    public TaskEntity convert(TaskCreateUpdateDTO dto) {
        TaskEntity entity = new TaskEntity();
        entity.setProject(dto.getProject().getUuid());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setImplementer(dto.getImplementer().getUuid());

        return entity;
    }
}
