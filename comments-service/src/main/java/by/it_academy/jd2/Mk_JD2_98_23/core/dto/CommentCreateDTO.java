package by.it_academy.jd2.Mk_JD2_98_23.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentCreateDTO {

    @JsonProperty("user")
    private Long user;

    @JsonProperty("task")
    private Long task;

    @JsonProperty("text")
    private String text;

    public CommentCreateDTO() {
    }

    public CommentCreateDTO(Long user, Long task, String text) {
        this.user = user;
        this.task = task;
        this.text = text;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getTask() {
        return task;
    }

    public void setTask(Long task) {
        this.task = task;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
