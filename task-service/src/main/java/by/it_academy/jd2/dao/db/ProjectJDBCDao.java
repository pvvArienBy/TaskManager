package by.it_academy.jd2.dao.db;

import by.it_academy.jd2.dao.repositories.IProjectDao;
import by.it_academy.jd2.dao.entity.ProjectEntity;

import java.util.List;

public class ProjectJDBCDao implements IProjectDao {

    @Override
    public ProjectEntity get(Long id) {
        return null;
    }

    @Override
    public ProjectEntity add(ProjectEntity item) {
        return null;
    }

    @Override
    public void remove(ProjectEntity item) {

    }

    @Override
    public List<ProjectEntity> get() {
        return null;
    }

    @Override
    public void update(ProjectEntity item) {

    }

    @Override
    public List<ProjectEntity> filterByName(String text) {
        return null;
    }
}
