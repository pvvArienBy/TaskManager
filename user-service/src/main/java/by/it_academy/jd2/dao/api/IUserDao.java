package by.it_academy.jd2.dao.api;

import by.it_academy.jd2.dao.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserDao extends JpaRepository<UserEntity, UUID> {

    @Override
    <S extends UserEntity> S save(S entity);

    @Override
    Optional<UserEntity> findById(UUID id);

    @Override
    Page<UserEntity> findAll(Pageable pageable);

    Optional<UserEntity> findByMail(String mail);

    boolean existsByMail(String mail);
}
