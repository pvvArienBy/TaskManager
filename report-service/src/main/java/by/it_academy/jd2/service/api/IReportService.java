package by.it_academy.jd2.service.api;

import by.it_academy.jd2.core.dto.ReportCreateDTO;
import by.it_academy.jd2.core.enums.EReportStatus;
import by.it_academy.jd2.core.enums.EType;
import by.it_academy.jd2.dao.entity.ReportEntity;
import org.example.mylib.tm.itacademy.dto.AuditDTO;
import org.example.mylib.tm.itacademy.dto.ParamDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.UUID;

public interface IReportService {

    Page<ReportEntity> getAll(PageRequest pageRequest);

    ReportEntity get(UUID uuid);

    ReportEntity save(ReportCreateDTO item);

    RedirectView getUrlReport(UUID uuid);

    boolean checkFileInData(UUID uuid);

    List<ReportEntity> getReportsWithTypeAndStatus(EType type, EReportStatus status);

    void setStatus(UUID uuid, EReportStatus reportStatus);

    List<AuditDTO> getListAudit(ParamDTO paramDTO);

    String getReportFileUrl(UUID uuid);
}
