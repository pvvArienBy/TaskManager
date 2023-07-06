package by.it_academy.jd2.dao.entity;

import by.it_academy.jd2.core.enums.ECategory;
import by.it_academy.jd2.core.enums.EPriority;
import by.it_academy.jd2.core.enums.EStatusTask;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tasks")
public class TaskEntity implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @JoinTable(
            name = "task_project",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    @ManyToOne
    private ProjectEntity project;

    private Long performer;

    @Enumerated(EnumType.STRING)
    private EStatusTask status;

    @Enumerated(EnumType.STRING)
    private EPriority priority;

    @Enumerated(EnumType.STRING)
    private ECategory category;

    private LocalDate term;

    @JoinTable(
            name = "task_file",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    @OneToMany
    private List<FileEntity> files;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Version
    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    public TaskEntity() {
    }

    public TaskEntity(Long id, String title, String description, ProjectEntity project, Long performer, EStatusTask status, EPriority priority, ECategory category, LocalDate term, List<FileEntity> files, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.project = project;
        this.performer = performer;
        this.status = status;
        this.priority = priority;
        this.category = category;
        this.term = term;
        this.files = files;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public Long getPerformer() {
        return performer;
    }

    public void setPerformer(Long performer) {
        this.performer = performer;
    }

    public EStatusTask getStatus() {
        return status;
    }

    public void setStatus(EStatusTask status) {
        this.status = status;
    }

    public EPriority getPriority() {
        return priority;
    }

    public void setPriority(EPriority priority) {
        this.priority = priority;
    }

    public ECategory getCategory() {
        return category;
    }

    public void setCategory(ECategory category) {
        this.category = category;
    }

    public LocalDate getTerm() {
        return term;
    }

    public void setTerm(LocalDate term) {
        this.term = term;
    }

    public List<FileEntity> getFiles() {
        return files;
    }

    public void setFiles(List<FileEntity> files) {
        this.files = files;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity that = (TaskEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(project, that.project) && Objects.equals(performer, that.performer) && status == that.status && priority == that.priority && category == that.category && Objects.equals(term, that.term) && Objects.equals(files, that.files) && Objects.equals(createDate, that.createDate) && Objects.equals(updateDate, that.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, project, performer, status, priority, category, term, files, createDate, updateDate);
    }
}
