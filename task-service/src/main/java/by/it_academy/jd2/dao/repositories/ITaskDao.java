package by.it_academy.jd2.dao.repositories;

import by.it_academy.jd2.dao.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ITaskDao extends JpaRepository<TaskEntity, UUID>, JpaSpecificationExecutor<TaskEntity> {
}

