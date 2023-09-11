package by.it_academy.jd2.dao.repositories;

import by.it_academy.jd2.core.enums.EReportStatus;
import by.it_academy.jd2.core.enums.EType;
import by.it_academy.jd2.dao.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IReportDao extends JpaRepository<ReportEntity, UUID> {
    ReportEntity findFileNameByUuid(UUID uuid);

    List<ReportEntity> findByTypeIsAndStatusIs(EType type, EReportStatus status);
}
