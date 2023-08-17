package by.it_academy.jd2.core.dto;

import by.it_academy.jd2.core.enums.ETaskStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaskCreateUpdateDTO {
    @NotBlank(message = "project - must not be empty")
    private RefDTO project;
    @NotBlank(message = "title - must not be empty")
    @Size(max = 255)
    private String title;
    @Size(max = 1000)
    private String description;
    @Enumerated(EnumType.STRING)
    private ETaskStatus status;
    private RefDTO implementer;

    public TaskCreateUpdateDTO() {
    }

    public TaskCreateUpdateDTO(RefDTO project, String title, String description,
                               ETaskStatus status, RefDTO implementer) {
        this.project = project;
        this.title = title;
        this.description = description;
        this.status = status;
        this.implementer = implementer;
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



