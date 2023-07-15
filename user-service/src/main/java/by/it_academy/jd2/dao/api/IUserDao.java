package by.it_academy.jd2.dao.api;

import by.it_academy.jd2.dao.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserDao extends CrudRepository<UserEntity, Long> {

    @Override
    Optional<UserEntity> findById(Long aLong);

    @Override
    List<UserEntity> findAll();

    @Override
    <S extends UserEntity> S save(S entity);

    @Override
    void delete(UserEntity entity);
}
