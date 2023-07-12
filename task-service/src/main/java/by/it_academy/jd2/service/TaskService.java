package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.CoordinatesDTO;
import by.it_academy.jd2.core.dto.ProjectCreateDTO;
import by.it_academy.jd2.dao.api.ITaskDao;
import by.it_academy.jd2.dao.entity.ProjectEntity;
import by.it_academy.jd2.service.api.IProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements IProjectService {

    private final ITaskDao taskDao;

    public TaskService(ITaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public List<ProjectEntity> get() {
        return null;
    }

    @Override
    public ProjectEntity get(Long id) {
        return null;
    }

    @Override
    public ProjectEntity add(ProjectCreateDTO item) {
        return null;
    }

    @Override
    public void remove(CoordinatesDTO coordinates) {

    }

    @Override
    public void update(CoordinatesDTO coordinates, ProjectCreateDTO item) {

    }

    @Override
    public List<ProjectEntity> filterByName(String text) {
        return null;
    }

    @Override
    public boolean validateCoordinatesParam(String id) {
        return false;
    }
}
