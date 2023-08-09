package by.it_academy.jd2.service.api;

import by.it_academy.jd2.core.dto.CoordinatesDTO;
import by.it_academy.jd2.core.dto.archtoDeelee.ProjectCreateDTO;
import by.it_academy.jd2.dao.entity.ProjectEntity;

public interface IProjectService extends ICRUDService <ProjectEntity, ProjectCreateDTO, CoordinatesDTO>, IFilterService <ProjectEntity>{

    boolean validateCoordinatesParam(String id);
}
