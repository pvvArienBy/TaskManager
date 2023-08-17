package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.ReportCreateDTO;
import by.it_academy.jd2.dao.entity.ReportEntity;
import by.it_academy.jd2.dao.repositories.IReportDao;
import by.it_academy.jd2.service.api.IParamService;
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

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReportService implements IReportService {
    private static final String REPORT_NOT_FOUND = "Report is not found";
    private static final String RESULT_NOT_FOUND = "RESULT is not found";

    private final IReportDao reportDao;
    private final IParamService paramService;
    private final IAuditClientService auditClientService;
    private final ConversionService conversionService;
    private final ApachePOIService apachePOIService;

    public ReportService(IReportDao reportDao, IParamService paramService, IAuditClientService auditClientService, ConversionService conversionService, ApachePOIService apachePOIService) {
        this.reportDao = reportDao;
        this.paramService = paramService;
        this.auditClientService = auditClientService;
        this.conversionService = conversionService;
        this.apachePOIService = apachePOIService;
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
        // TODO: 17.08.2023 заложить email в отчет или fio
        ReportEntity entity = Objects.requireNonNull(
                conversionService
                        .convert(item, ReportEntity.class));
        entity.setUuid(UUID.randomUUID());

        this.paramService.save(entity.getParamEntity());

        this.reportDao.saveAndFlush(entity);

        try {
            testGetReportFile(conversionService.convert(entity.getParamEntity(), ParamDTO.class));
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO: 17.08.2023  
        }

        return entity;
    }


    private void testGetReportFile(ParamDTO dto) throws IOException {
        List<AuditDTO> dtoList = Optional
                .ofNullable(
                        this.auditClientService.getList(dto)
                                .getBody())
                .orElseThrow(()
                        -> new ResultNotFoundException(RESULT_NOT_FOUND));

        String filePath = dto.getUser().toString(); // TODO: 17.08.2023

        this.apachePOIService.convertToExcel(dtoList,filePath);

    }
}

