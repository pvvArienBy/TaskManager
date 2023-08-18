package by.it_academy.jd2.core.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReportFileCreateDTO {
    private String fileName;
    private UUID report;
    private LocalDateTime dtCreate;

    public ReportFileCreateDTO() {
    }

    public ReportFileCreateDTO(String fileName, UUID report, LocalDateTime dtCreate) {
        this.fileName = fileName;
        this.report = report;
        this.dtCreate = dtCreate;
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
