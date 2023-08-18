package by.it_academy.jd2.dao.repositories;

import by.it_academy.jd2.dao.entity.ReportFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IReportFileDao extends JpaRepository<ReportFileEntity, Long> {
    ReportFileEntity findByReportUuid(UUID uuid);
}
