package by.it_academy.jd2.core.dto;

public class FileDTO {

    private Long id;

    private String name;

    private String path;

    private String contentType;

    public FileDTO() {
    }

    public FileDTO(Long id, String name, String path, String contentType) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.contentType = contentType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}