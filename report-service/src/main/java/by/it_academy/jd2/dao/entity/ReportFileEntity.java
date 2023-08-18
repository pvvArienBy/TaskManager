package by.it_academy.jd2.dao.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "files_info")
public class ReportFileEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String fileName;
    @JoinTable(
            name = "report_file",
            joinColumns = @JoinColumn(name = "file_id"),
            inverseJoinColumns = @JoinColumn(name = "reports_id")
    )
    @ManyToOne
    private ReportEntity report;
    @CreationTimestamp
    @Column(name = "date_create", nullable = false)
    private LocalDateTime dtCreate;

    public ReportFileEntity() {
    }

    public ReportFileEntity(Long id, String fileName, ReportEntity report, LocalDateTime dtCreate) {
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

    public ReportEntity getReport() {
        return report;
    }

    public void setReport(ReportEntity report) {
        this.report = report;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportFileEntity that = (ReportFileEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(fileName, that.fileName) &&
                Objects.equals(report, that.report) && Objects.equals(dtCreate, that.dtCreate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileName, report, dtCreate);
    }
}
