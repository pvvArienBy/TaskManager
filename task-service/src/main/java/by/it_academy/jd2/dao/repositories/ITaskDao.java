package by.it_academy.jd2.dao.repositories;

import by.it_academy.jd2.dao.entity.TaskEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ITaskDao extends JpaRepository<TaskEntity, UUID> {
    @Query("SELECT t FROM TaskEntity t WHERE t.project IN :projectUuids")
    Page<TaskEntity> findByProjectUuids(@Param("projectUuids") List<UUID> projectUuids, Pageable pageable);
}

