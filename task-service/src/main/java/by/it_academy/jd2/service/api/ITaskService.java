package by.it_academy.jd2.service.api;

import by.it_academy.jd2.core.dto.CoordinatesDTO;
import by.it_academy.jd2.core.dto.TaskCreateDTO;
import by.it_academy.jd2.dao.entity.TaskEntity;

public interface ITaskService extends ICRUDService <TaskEntity, TaskCreateDTO, CoordinatesDTO>, IFilterService <TaskEntity>{

    boolean validateCoordinatesParam(String id);
}
