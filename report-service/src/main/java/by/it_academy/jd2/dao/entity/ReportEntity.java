package by.it_academy.jd2.dao.entity;

import by.it_academy.jd2.core.enums.EReportStatus;
import by.it_academy.jd2.core.enums.EType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "reports")
public class ReportEntity implements Serializable {
    static final long serialVersionUID = 3L;
    @Id
    private UUID uuid;
    @CreationTimestamp
    @Column(name = "date_create", nullable = false)
    private LocalDateTime dtCreate;
    @Version
    @UpdateTimestamp
    @Column(name = "date_update", nullable = false)
    private LocalDateTime dtUpdate;
    @Enumerated(EnumType.STRING)
    private EReportStatus status;
    @Enumerated(EnumType.STRING)
    private EType type;
    @Size(max = 500)
    private String description;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "report_url")
    private String reportUrl;
    @JoinTable(
            name = "reports_param",
            joinColumns = @JoinColumn(name = "reports_id"),
            inverseJoinColumns = @JoinColumn(name = "param_id")
    )
    @ManyToOne
    private ParamEntity paramEntity;

    public ReportEntity() {
    }

    public ReportEntity(UUID uuid, LocalDateTime dtCreate,
                        LocalDateTime dtUpdate, EReportStatus status,
                        EType type, String description, ParamEntity paramEntity,
                        String reportUrl) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.status = status;
        this.type = type;
        this.description = description;
        this.paramEntity = paramEntity;
        this.reportUrl = reportUrl;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
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

    public ParamEntity getParamEntity() {
        return paramEntity;
    }

    public void setParamEntity(ParamEntity paramEntity) {
        this.paramEntity = paramEntity;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportEntity that = (ReportEntity) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(dtCreate, that.dtCreate) &&
                Objects.equals(dtUpdate, that.dtUpdate) && status == that.status && type == that.type &&
                Objects.equals(description, that.description) && Objects.equals(paramEntity, that.paramEntity) &&
                Objects.equals(reportUrl, that.reportUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, status, type, description, paramEntity, reportUrl);
    }
}

