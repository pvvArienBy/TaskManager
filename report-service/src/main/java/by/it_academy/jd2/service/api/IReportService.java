package by.it_academy.jd2.service.api;

import by.it_academy.jd2.core.dto.ReportCreateDTO;
import by.it_academy.jd2.dao.entity.ReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;

public interface IReportService {

    Page<ReportEntity> getAll(PageRequest pageRequest);

    ReportEntity get(UUID uuid);

    ReportEntity save(ReportCreateDTO item);

    RedirectView getUrlReport(UUID uuid);

    String getFileName(UUID uuid);

    boolean checkFileInData(UUID uuid);
}
