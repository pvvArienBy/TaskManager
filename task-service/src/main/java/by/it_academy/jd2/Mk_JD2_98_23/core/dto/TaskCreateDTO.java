package by.it_academy.jd2.Mk_JD2_98_23.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TaskCreateDTO {

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("project")
    private Long project;

    @JsonProperty("performer")
    private Long performer;

    @JsonProperty("status")
    private String status;

    @JsonProperty("priority")
    private String priority ;

    @JsonProperty("category")
    private String category ;

    @JsonProperty("term")
    private LocalDate term ;

    @JsonProperty("files")
    private List<String> files;

    public TaskCreateDTO() {
    }

    public TaskCreateDTO(String title, String description, Long project, Long performer, String status, String priority, String category, LocalDate term, List<String> files) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getTerm() {
        return term;
    }

    public void setTerm(LocalDate term) {
        this.term = term;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
}
