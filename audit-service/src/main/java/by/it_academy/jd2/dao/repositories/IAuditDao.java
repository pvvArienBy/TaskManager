package by.it_academy.jd2.dao.repositories;

import by.it_academy.jd2.dao.entity.AuditEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IAuditDao extends JpaRepository<AuditEntity, UUID> {
    @Override
    <S extends AuditEntity> S save(S entity);

    @Override
    Optional<AuditEntity> findById(UUID id);

    @Override
    Page<AuditEntity> findAll(Pageable pageable);
}
