package by.it_academy.jd2.service;

import by.it_academy.jd2.dao.repositories.IProjectDao;
import by.it_academy.jd2.service.api.IProjectService;
import org.springframework.stereotype.Service;

@Service
public class ProjectService implements IProjectService {

    private final IProjectDao projectDao;

    public ProjectService(IProjectDao projectDao) {
        this.projectDao = projectDao;
    }

}
