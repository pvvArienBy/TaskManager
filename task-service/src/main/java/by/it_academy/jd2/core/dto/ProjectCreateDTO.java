package by.it_academy.jd2.core.dto;

import java.util.List;

public class ProjectCreateDTO {

    private String name;

    private String description;

    private Long creator;

    private List<Long> users;

    private Long version;

    public ProjectCreateDTO() {
    }

    public ProjectCreateDTO(String name, String description, Long creator, List<Long> users, Long version) {
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.users = users;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public List<Long> getUsers() {
        return users;
    }

    public void setUsers(List<Long> users) {
        this.users = users;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
