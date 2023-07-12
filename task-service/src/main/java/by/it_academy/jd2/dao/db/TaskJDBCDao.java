package by.it_academy.jd2.dao.db;

import by.it_academy.jd2.dao.api.ITaskDao;
import by.it_academy.jd2.dao.entity.TaskEntity;

import java.util.List;

public class TaskJDBCDao implements ITaskDao {

    @Override
    public TaskEntity get(Long id) {
        return null;
    }

    @Override
    public TaskEntity add(TaskEntity item) {
        return null;
    }

    @Override
    public void remove(TaskEntity item) {

    }

    @Override
    public List<TaskEntity> get() {
        return null;
    }

    @Override
    public void update(TaskEntity item) {

    }

    @Override
    public List<TaskEntity> filterByName(String text) {
        return null;
    }
}
