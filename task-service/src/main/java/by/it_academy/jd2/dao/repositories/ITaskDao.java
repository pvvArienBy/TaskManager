package by.it_academy.jd2.dao.repositories;

import by.it_academy.jd2.dao.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ITaskDao extends JpaRepository<TaskEntity, UUID> {

//    Optional<TaskEntity> findByMail(String mail);
//
//    boolean existsByMail(String mail); // TODO: 09.08.2023  
}