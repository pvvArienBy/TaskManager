package by.it_academy.jd2.core.dto;

import by.it_academy.jd2.core.enums.ETaskStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// TODO: 11.08.2023 описание и статус и implementer могут быть null // project NotNull
public class TaskCreateUpdateDTO {
    @NotBlank(message = "project не должен быть пустым")
    private ProjectRefDTO project;
    @NotBlank(message = "title не должен быть пустым")
    @Size(max = 255)
    private String title;
    @Size(max = 1000)
    private String description;
    @Enumerated(EnumType.STRING)
    private ETaskStatus status;
    private UserRefDTO implementer;

    public TaskCreateUpdateDTO() {
    }

    public TaskCreateUpdateDTO(ProjectRefDTO project, String title, String description,
                               ETaskStatus status, UserRefDTO implementer) {
        this.project = project;
        this.title = title;
        this.description = description;
        this.status = status;
        this.implementer = implementer;
    }

    public ProjectRefDTO getProject() {
        return project;
    }

    public void setProject(ProjectRefDTO project) {
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

    public UserRefDTO getImplementer() {
        return implementer;
    }

    public void setImplementer(UserRefDTO implementer) {
        this.implementer = implementer;
    }
}



