package by.it_academy.jd2.dao.repositories;

import by.it_academy.jd2.dao.entity.UserEntity;
import org.example.mylib.tm.itacademy.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserDao extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByMail(String mail);

    boolean existsByMail(String mail);

    long countByUuidIn(List<UUID> uuidList);

    boolean existsByUuidAndRole(UUID uuid, ERole role);

}
