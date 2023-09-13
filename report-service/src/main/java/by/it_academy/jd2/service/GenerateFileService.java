package by.it_academy.jd2.service;

import by.it_academy.jd2.core.enums.EReportStatus;
import by.it_academy.jd2.core.enums.EType;
import by.it_academy.jd2.dao.entity.ReportEntity;
import by.it_academy.jd2.dao.entity.ReportFileEntity;
import by.it_academy.jd2.service.api.IFileService;
import by.it_academy.jd2.service.api.IReportFileService;
import by.it_academy.jd2.service.api.IReportService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.mylib.tm.itacademy.dto.AuditDTO;
import org.example.mylib.tm.itacademy.dto.ParamDTO;
import org.example.mylib.tm.itacademy.exceptions.MinioClientException;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import java.util.function.Function;

@Service
public class GenerateFileService {
    private final IReportService reportService;
    private final ConversionService conversionService;
    private final IReportFileService reportFileService;
    private final IFileService fileService;

    private Function<UUID, String> fileNameFromUuidFunction = (x) -> x.toString().concat(".xlsx");
    private Function<EType, String> bucketNameFromReportTypeFunction = (x) -> "journal_audit";

    public GenerateFileService(IReportService reportService, ConversionService conversionService,
                               IReportFileService reportFileService, IFileService fileService) {
        this.reportService = reportService;
        this.conversionService = conversionService;
        this.reportFileService = reportFileService;
        this.fileService = fileService;
    }

    @Scheduled(fixedDelay = 30000)
    public void performScheduledReportFormingAndSending() {
        List<ReportEntity> reports = reportService
                .getReportsWithTypeAndStatus(EType.JOURNAL_AUDIT, EReportStatus.LOADED);

        for (ReportEntity report : reports) {
            UUID uuid = report.getUuid();

            try {
                reportService.setStatus(uuid, EReportStatus.PROGRESS);
                formAndSendReport(report);
                reportService.setStatus(uuid, EReportStatus.DONE);
            } catch (MinioClientException e) {
                reportService.setStatus(uuid, EReportStatus.ERROR);
            }
        }
    }

    private void formAndSendReport(ReportEntity entity) {
        String fileName = null;

        try {
            ReportFileEntity fileEntity = new ReportFileEntity();

            List<AuditDTO> auditDTOList = reportService.getListAudit(
                    conversionService.convert(entity.getParamEntity(), ParamDTO.class));

            UUID reportUuid = entity.getUuid();
            fileName = createFileWithReports(auditDTOList, reportUuid, this.fileNameFromUuidFunction);

            fileEntity.setFileName(fileName);
            fileEntity.setReport(entity);
            fileEntity.setBucketName(formBucketName(entity.getType(), this.bucketNameFromReportTypeFunction));

            fileService.saveFile(fileName, fileEntity.getBucketName());

            reportFileService.save(fileEntity);

        } catch (Exception e) {
            throw new MinioClientException(e.getMessage());
        } finally {
            if (fileName != null) {
                File file = new File(fileName);
                file.delete();
            }
        }
    }

    private <T> String createFileWithReports(List<AuditDTO> auditDTOList, T source, Function<T, String> formReportFileName) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("report");
            int rowNum = 0;

            createTopRow(workbook, sheet, rowNum++);
            createHeaderRow(workbook, sheet, rowNum++);

            for (AuditDTO auditDTO : auditDTOList) {
                fillRowWithData(auditDTO, workbook, sheet, rowNum++);
            }

            for (int i = 0; i < 9; i++) {
                sheet.autoSizeColumn(i);
            }

            String fileName = fileNameFormer(source, formReportFileName);

            try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
                workbook.write(fileOutputStream);
                workbook.close();
            }

            return fileName;
        }
    }

    private void fillRowWithData(AuditDTO auditDTO, Workbook workbook, Sheet sheet, int rowNum) {
        Row contextRow = sheet.createRow(rowNum);

        contextRow.createCell(0).setCellValue(auditDTO.getUuid().toString());

        CellStyle dtUpdateCellStyle = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        dtUpdateCellStyle.setDataFormat(
                creationHelper.createDataFormat().getFormat("dd-mm-yyyy h:mm:ss"));

        Cell dtUpdateCell = contextRow.createCell(1);
        dtUpdateCell.setCellStyle(dtUpdateCellStyle);
        Long dtCreate = auditDTO.getDtCreate();
        LocalDateTime converterLocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(dtCreate),
                TimeZone.getDefault().toZoneId());
        dtUpdateCell.setCellValue(converterLocalDateTime);

        contextRow.createCell(2).setCellValue(auditDTO.getUser().getUuid().toString());
        contextRow.createCell(3).setCellValue(auditDTO.getUser().getMail());
        contextRow.createCell(4).setCellValue(auditDTO.getUser().getFio());
        contextRow.createCell(5).setCellValue(auditDTO.getUser().getRole().toString());
        contextRow.createCell(6).setCellValue(auditDTO.getText());
        contextRow.createCell(7).setCellValue(auditDTO.getType().toString());
        contextRow.createCell(8).setCellValue(auditDTO.getId());
    }

    private static void createHeaderRow(Workbook workbook, Sheet sheet, int rowNum) {
        Row headerRow = sheet.createRow(rowNum);
        headerRow.createCell(0).setCellValue("uuid");
        headerRow.createCell(1).setCellValue("dt_create");
        headerRow.createCell(2).setCellValue("uuid");
        headerRow.createCell(3).setCellValue("mail");
        headerRow.createCell(4).setCellValue("fio");
        headerRow.createCell(5).setCellValue("role");
        headerRow.createCell(6).setCellValue("text");
        headerRow.createCell(7).setCellValue("type");
        headerRow.createCell(8).setCellValue("id");
    }

    private static void createTopRow(Workbook workbook, Sheet sheet, int rowNum) {
        Row userHeaderRow = sheet.createRow(rowNum);
        Cell userCell = userHeaderRow.createCell(2);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        userCell.setCellStyle(cellStyle);
        userCell.setCellValue("user");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 5));
    }

    private <T> String fileNameFormer(T source, Function<T, String> formReportFileNameFunction) {
        return formReportFileNameFunction.apply(source);
    }

    private <T> String formBucketName(T source, Function<T, String> formBucketFileNameFunction) {
        return formBucketFileNameFunction.apply(source);
    }
}
