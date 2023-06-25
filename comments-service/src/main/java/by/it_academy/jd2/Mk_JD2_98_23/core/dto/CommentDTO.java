package by.it_academy.jd2.Mk_JD2_98_23.core.dto;

public class CommentDTO {

    private Long id;

    //todo temporary
    private String nameUser;

    //todo temporary
    private String nameTask;

    private String text;

    private Long version;

    public CommentDTO() {
    }

    public CommentDTO(Long id, String nameUser, String nameTask, String text, Long version) {
        this.id = id;
        this.nameUser = nameUser;
        this.nameTask = nameTask;
        this.text = text;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
