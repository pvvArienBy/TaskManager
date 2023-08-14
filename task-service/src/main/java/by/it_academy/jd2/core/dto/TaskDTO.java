package by.it_academy.jd2.core.dto;

import by.it_academy.jd2.core.enums.ETaskStatus;

import java.util.UUID;

public class TaskDTO {
    private UUID uuid;
    private Long dtCreate;
    private Long dtUpdate;
    private RefDTO project;
    private String title;
    private String description;
    private ETaskStatus status;
    private RefDTO implementer;

    public TaskDTO() {
    }

    public TaskDTO(UUID uuid, Long dtCreate, Long dtUpdate,
                   RefDTO project, String title, String description,
                   ETaskStatus status, RefDTO implementer) {
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

    public RefDTO getProject() {
        return project;
    }

    public void setProject(RefDTO project) {
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

    public RefDTO getImplementer() {
        return implementer;
    }

    public void setImplementer(RefDTO implementer) {
        this.implementer = implementer;
    }
}
