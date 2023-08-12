package by.it_academy.jd2.dao.entity;

import by.it_academy.jd2.core.enums.ETaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "tasks")
public class TaskEntity implements Serializable {
    static final long serialVersionUID = 1L;
    @Id
    private UUID uuid;
    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "date_create")
    private LocalDateTime dtCreate;
    @Version
    @UpdateTimestamp(source = SourceType.DB)
    @Column(name = "date_update")
    private LocalDateTime dtUpdate;
    @JoinColumn(name = "project_uuid", nullable = false)
    private UUID project;
    @Size(max = 255)
    @Column(nullable = false)
    private String title;
    @Size(max = 1000)
    @Column(nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ETaskStatus status;
    @JoinColumn(name = "implementer_uuid")
    private UUID implementer;

    public TaskEntity() {
    }

    public TaskEntity(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate,
                      UUID project, String title, String description,
                      ETaskStatus status, UUID implementer) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.project = project;
        this.title = title;
        this.description = description;
        this.status = status;
        this.implementer = implementer;
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

    public UUID getProject() {
        return project;
    }

    public void setProject(UUID project) {
        this.project = project;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ETaskStatus getStatus() {
        return status;
    }

    public void setStatus(ETaskStatus status) {
        this.status = status;
    }

    public UUID getImplementer() {
        return implementer;
    }

    public void setImplementer(UUID implementer) {
        this.implementer = implementer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity that = (TaskEntity) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(dtCreate, that.dtCreate) &&
                Objects.equals(dtUpdate, that.dtUpdate) && Objects.equals(project, that.project) &&
                Objects.equals(title, that.title) && Objects.equals(description, that.description) &&
                status == that.status && Objects.equals(implementer, that.implementer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, project, title, description, status, implementer);
    }
}
