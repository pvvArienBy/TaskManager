package by.it_academy.jd2.core.dto;

import by.it_academy.jd2.core.enums.ECategory;
import by.it_academy.jd2.core.enums.EPriority;
import by.it_academy.jd2.core.enums.EStatusTask;

import java.time.LocalDate;
import java.util.List;

public class TaskCreateDTO {

    private String title;

    private String description;

    private Long project;

    private Long performer;

    private EStatusTask status;

    private EPriority priority ;

    private ECategory category ;

    private LocalDate term ;

    private List<FileCreateDTO> files;

    public TaskCreateDTO() {
    }

    public TaskCreateDTO(String title, String description, Long project, Long performer, EStatusTask status, EPriority priority, ECategory category, LocalDate term, List<FileCreateDTO> files) {
        this.title = title;
        this.description = description;
        this.project = project;
        this.performer = performer;
        this.status = status;
        this.priority = priority;
        this.category = category;
        this.term = term;
        this.files = files;
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

    public Long getProject() {
        return project;
    }

    public void setProject(Long project) {
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

    public List<FileCreateDTO> getFiles() {
        return files;
    }

    public void setFiles(List<FileCreateDTO> files) {
        this.files = files;
    }
}
