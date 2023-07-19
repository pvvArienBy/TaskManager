package by.it_academy.jd2.dao.api;

import by.it_academy.jd2.dao.entity.UserEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserDao extends ListCrudRepository<UserEntity, UUID> {

    @Override
    List<UserEntity> findAll();

    @Override
    <S extends UserEntity> S save(S entity);

    @Override
    Optional<UserEntity> findById(UUID id);

}
