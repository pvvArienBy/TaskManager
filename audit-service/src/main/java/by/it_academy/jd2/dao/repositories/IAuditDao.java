package by.it_academy.jd2.dao.repositories;

import by.it_academy.jd2.dao.entity.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface IAuditDao extends JpaRepository<AuditEntity, UUID> {

    List<AuditEntity> findByUserUuidAndDtCreateBetween(UUID userUuid, LocalDateTime fromDate, LocalDateTime toDate);
}
