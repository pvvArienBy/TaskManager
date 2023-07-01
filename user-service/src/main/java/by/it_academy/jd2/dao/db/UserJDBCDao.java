package by.it_academy.jd2.dao.db;

import by.it_academy.jd2.dao.api.IUserDao;
import by.it_academy.jd2.dao.entity.UserEntity;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class UserJDBCDao implements IUserDao {

    private EntityManagerFactory em;

    public UserJDBCDao(EntityManagerFactory em) {
        this.em = em;
    }

    @Override
    public List<UserEntity> get() {
        return null;
    }

    @Override
    public UserEntity get(Long id) {
        return null;
    }

    @Override
    public UserEntity add(UserEntity item) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void update(UserEntity item) {

    }
}
