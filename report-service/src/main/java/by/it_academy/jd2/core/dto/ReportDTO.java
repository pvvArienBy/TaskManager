package by.it_academy.jd2.core.dto;

import by.it_academy.jd2.core.enums.EReportStatus;
import by.it_academy.jd2.core.enums.EType;
import org.example.mylib.tm.itacademy.dto.ParamDTO;

import java.util.Map;
import java.util.UUID;

public class ReportDTO {
    private UUID uuid;
    private Long dtCreate;
    private Long dtUpdate;
    private EReportStatus status;
    private EType type;
    private String description;
    private ParamDTO params;
    private String reportUrl;

    public ReportDTO() {
    }

    public ReportDTO(UUID uuid, Long dtCreate, Long dtUpdate,
                     EReportStatus status, EType type, String description,
                     ParamDTO params, String reportUrl) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.status = status;
        this.type = type;
        this.description = description;
        this.params = params;
        this.reportUrl = reportUrl;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Long getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Long dtCreate) {
        this.dtCreate = dtCreate;
    }

    public Long getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(Long dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public EReportStatus getStatus() {
        return status;
    }

    public void setStatus(EReportStatus status) {
        this.status = status;
    }

    public EType getType() {
        return type;
    }

    public void setType(EType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ParamDTO getParams() {
        return params;
    }

    public void setParams(ParamDTO params) {
        this.params = params;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }
}
