package by.it_academy.jd2.core.dto;

public class FileCreateDTO {

    private String name;

    private String path;

    private String contentType;

    public FileCreateDTO() {
    }

    public FileCreateDTO(String name, String path, String contentType) {
        this.name = name;
        this.path = path;
        this.contentType = contentType;
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
