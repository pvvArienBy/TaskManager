package by.it_academy.jd2.core.dto;

public class UserShortDTO {

    private Long id;

    private String name;

    public UserShortDTO() {
    }

    public UserShortDTO(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
