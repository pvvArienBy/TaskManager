package by.it_academy.jd2.dao.entity;

import by.it_academy.jd2.core.enums.EProjectStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "projects")
public class ProjectEntity implements Serializable {
    static final long serialVersionUID = 2L;
    @Id
    private UUID uuid;
    @CreationTimestamp
    @Column(name = "date_create")
    private LocalDateTime dtCreate;
    @Version
    @UpdateTimestamp
    @Column(name = "date_update")
    private LocalDateTime dtUpdate;
    @Size(max = 255)
    @Column(nullable = false)
    private String name;
    @Size(max = 1000)
    @Column(nullable = false)
    private String description;
    @JoinColumn( name = "manager_uuid", nullable = false)
    private UUID manager;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "project_staff", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "staff")
    private List<UUID> staff;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EProjectStatus status;

    public ProjectEntity() {
    }

    public ProjectEntity(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate,
                         String name, String description, UUID manager,
                         List<UUID> staff, EProjectStatus status) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.staff = staff;
        this.status = status;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getManager() {
        return manager;
    }

    public void setManager(UUID manager) {
        this.manager = manager;
    }

    public List<UUID> getStaff() {
        return staff;
    }

    public void setStaff(List<UUID> staff) {
        this.staff = staff;
    }

    public EProjectStatus getStatus() {
        return status;
    }

    public void setStatus(EProjectStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectEntity that = (ProjectEntity) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(dtCreate, that.dtCreate) &&
                Objects.equals(dtUpdate, that.dtUpdate) && Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) && Objects.equals(manager, that.manager)
                && Objects.equals(staff, that.staff) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, name, description, manager, staff, status);
    }
}
