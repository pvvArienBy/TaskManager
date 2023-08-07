package by.it_academy.jd2.dao.repositories;

import by.it_academy.jd2.dao.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IConfirmationTokenDao
        extends JpaRepository<TokenEntity, Long> {

    Optional<TokenEntity> findByToken(UUID token);
}
