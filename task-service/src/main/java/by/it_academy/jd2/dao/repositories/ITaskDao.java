package by.it_academy.jd2.dao.repositories;

import by.it_academy.jd2.core.enums.ETaskStatus;
import by.it_academy.jd2.dao.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ITaskDao extends JpaRepository<TaskEntity, UUID> {

    Page<TaskEntity> findByProjectIn(List<UUID> projects, Pageable pageable);

    Page<TaskEntity> findByImplementerIn(List<UUID> implementers, Pageable pageable);

    Page<TaskEntity> findByProjectInAndImplementerIn(List<UUID> projects, List<UUID> implementers, Pageable pageable);

    Page<TaskEntity> findByStatusIn(List<ETaskStatus> status, Pageable pageable);

    Page<TaskEntity> findByProjectInAndStatusIn(List<UUID> projects, List<ETaskStatus> status,
                                                Pageable pageable);

    Page<TaskEntity> findByImplementerInAndStatusIn(List<UUID> implementer, List<ETaskStatus> status,
                                                    Pageable pageable);

    Page<TaskEntity> findByProjectInAndImplementerInAndStatusIn(List<UUID> projects, List<UUID> implementer,
                                                                List<ETaskStatus> status, Pageable pageable);
}

