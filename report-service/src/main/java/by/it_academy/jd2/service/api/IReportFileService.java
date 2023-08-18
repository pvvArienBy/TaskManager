package by.it_academy.jd2.service.api;

import by.it_academy.jd2.dao.entity.ReportFileEntity;
import java.util.UUID;

public interface IReportFileService {
    void save(ReportFileEntity item);
    ReportFileEntity findFileNameByReport(UUID uuid);
}
