package by.it_academy.jd2.dao.repositories;

import by.it_academy.jd2.dao.entity.ReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IReportDao extends JpaRepository<ReportEntity, UUID> {

    // TODO: 17.08.2023
//    @Override
//    <S extends ReportEntity> S save(S entity);
//
//    @Override
//    Optional<ReportEntity> findById(UUID id);
//
//    @Override
//    Page<ReportEntity> findAll(Pageable pageable);
}
