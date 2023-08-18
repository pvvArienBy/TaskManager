package by.it_academy.jd2.service;

import by.it_academy.jd2.dao.entity.ReportFileEntity;
import by.it_academy.jd2.dao.repositories.IReportFileDao;
import by.it_academy.jd2.service.api.IReportFileService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReportFileService implements IReportFileService {
    private final IReportFileDao reportFileDao;

    public ReportFileService(IReportFileDao reportFileDao) {
        this.reportFileDao = reportFileDao;
    }

    @Override
    public void save(ReportFileEntity item) {
        this.reportFileDao.saveAndFlush(item);
    }

    @Override
    public ReportFileEntity findFileNameByReport(UUID uuid) {
        return this.reportFileDao.findByReportUuid(uuid);
    }

    @Override
    public boolean checkFileInData(UUID uuid) {
        return this.reportFileDao.existsByReportUuid(uuid);
    }
}
