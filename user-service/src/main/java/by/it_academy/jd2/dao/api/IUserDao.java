package by.it_academy.jd2.dao.api;

import by.it_academy.jd2.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserDao extends JpaRepository<UserEntity, UUID> {
    @Override
    List<UserEntity> findAll();
    @Override
    @Validated
    <S extends UserEntity> S save(S entity);
    @Override
    Optional<UserEntity> findById(UUID id);
    Optional<UserEntity> findByMail(String mail);
}
