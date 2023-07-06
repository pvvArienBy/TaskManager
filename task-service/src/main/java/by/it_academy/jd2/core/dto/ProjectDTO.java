package by.it_academy.jd2.core.dto;

import java.util.List;

public class ProjectDTO {

    private Long id;

    private String name;

    private String description;

    private UserShortDTO creator;

    private List<UserShortDTO> users;

    private Long version;

    public ProjectDTO() {
    }

    public ProjectDTO(Long id, String name, String description, UserShortDTO creator, List<UserShortDTO> users, Long version) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.users = users;
        this.version = version;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserShortDTO getCreator() {
        return creator;
    }

    public void setCreator(UserShortDTO creator) {
        this.creator = creator;
    }

    public List<UserShortDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserShortDTO> users) {
        this.users = users;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
