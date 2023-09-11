package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.ReportCreateDTO;
import by.it_academy.jd2.core.enums.EReportStatus;
import by.it_academy.jd2.core.enums.EType;
import by.it_academy.jd2.dao.entity.ReportEntity;
import by.it_academy.jd2.dao.entity.ReportFileEntity;
import by.it_academy.jd2.dao.repositories.IReportDao;
import by.it_academy.jd2.service.api.IFileService;
import by.it_academy.jd2.service.api.IParamService;
import by.it_academy.jd2.service.api.IReportFileService;
import by.it_academy.jd2.service.api.IReportService;
import by.it_academy.jd2.service.api.feign.IAuditClientService;
import org.example.mylib.tm.itacademy.dto.AuditDTO;
import org.example.mylib.tm.itacademy.dto.ParamDTO;
import org.example.mylib.tm.itacademy.exceptions.EntityNotFoundException;
import org.example.mylib.tm.itacademy.exceptions.ResultNotFoundException;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReportService implements IReportService {
    private static final String REPORT_NOT_FOUND = "Report is not found";
    private static final String RESULT_NOT_FOUND = "Result is not found";
    private static final String FILE_NOT_CREATED = "File generation and saving error";

    private final IReportDao reportDao;
    private final IParamService paramService;
    private final IAuditClientService auditClientService;
    private final ConversionService conversionService;
    private final IReportFileService reportFileService;
    private final IFileService fileService;

    public ReportService(IReportDao reportDao, IParamService paramService,
                         IAuditClientService auditClientService, ConversionService conversionService,
                         ReportFileService reportFileService, IFileService fileService) {
        this.reportDao = reportDao;
        this.paramService = paramService;
        this.auditClientService = auditClientService;
        this.conversionService = conversionService;
        this.reportFileService = reportFileService;
        this.fileService = fileService;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ReportEntity> getAll(PageRequest pageRequest) {
        return this.reportDao.findAll(pageRequest);
    }

    @Transactional(readOnly = true)
    @Override
    public ReportEntity get(UUID uuid) {
        ReportEntity entity = this.reportDao
                .findById(uuid)
                .orElseThrow(()
                        -> new EntityNotFoundException(REPORT_NOT_FOUND));

        return entity;
    }

    @Transactional
    @Override
    public ReportEntity save(ReportCreateDTO item) {
        ReportEntity entity = Objects.requireNonNull(
                conversionService
                        .convert(item, ReportEntity.class));
        entity.setUuid(UUID.randomUUID());

        this.paramService.save(entity.getParamEntity());
        this.reportDao.saveAndFlush(entity);

        return entity;
    }

    @Override
    public RedirectView getUrlReport(UUID uuid) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(getReportFileUrl(uuid));

        return redirectView;
    }

    @Transactional(readOnly = true)
    @Override
    public String getFileName(UUID uuid) {
        return this.reportFileService.findFileNameByReport(uuid).getFileName();
    }

    @Transactional(readOnly = true)
    @Override
    public boolean checkFileInData(UUID uuid) {
        return this.reportFileService.checkFileInData(uuid);
    }

    @Override
    public List<AuditDTO> getListAudit(ParamDTO paramDTO) {
        return Optional
                .ofNullable(
                        this.auditClientService.getList(paramDTO)
                                .getBody())
                .orElseThrow(()
                        -> new ResultNotFoundException(RESULT_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportEntity> getReportsWithTypeAndStatus(EType type, EReportStatus status) {
        return reportDao.findByTypeIsAndStatusIs(type, status);
    }

    @Override
    @Transactional
    public void setStatus(UUID uuid, EReportStatus reportStatus) {
        ReportEntity entity = this.reportDao
                .findById(uuid)
                .orElseThrow(()
                        -> new EntityNotFoundException(REPORT_NOT_FOUND));

        entity.setStatus(reportStatus);
        reportDao.saveAndFlush(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public String getReportFileUrl(UUID uuid) {
        ReportFileEntity reportInfo = reportFileService.findFileNameByReport(uuid);

        return fileService.getFileUrl(reportInfo.getFileName(), reportInfo.getBucketName());
    }

}

