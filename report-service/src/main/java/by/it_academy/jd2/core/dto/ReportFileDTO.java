package by.it_academy.jd2.core.dto;

import by.it_academy.jd2.dao.entity.ReportEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReportFileDTO {
    private Long id;
    private String fileName;
    private UUID report;
    private LocalDateTime dtCreate;

    public ReportFileDTO() {
    }

    public ReportFileDTO(Long id, String fileName, UUID report, LocalDateTime dtCreate) {
        this.id = id;
        this.fileName = fileName;
        this.report = report;
        this.dtCreate = dtCreate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public UUID getReport() {
        return report;
    }

    public void setReport(UUID report) {
        this.report = report;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }
}
