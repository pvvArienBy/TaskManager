package by.it_academy.jd2.core.dto;

import by.it_academy.jd2.core.enums.EProjectStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ProjectCreateUpdateDTO {
    @NotBlank(message = "name не должен быть пустым")
    @Size(max = 255)
    private String name;
    @NotBlank(message = "description не должен быть пустым")
    @Size(max = 1000)
    private String description;
    @NotBlank(message = "manager не должен быть пустым")
    private UserRefDTO manager;
    private List<UserRefDTO> staff;
    @NotNull(message = "status не должен быть пустым")
    @Enumerated(EnumType.STRING)
    private EProjectStatus status;

    public ProjectCreateUpdateDTO() {
    }

    public ProjectCreateUpdateDTO(String name, String description, UserRefDTO manager,
                                  List<UserRefDTO> staff, EProjectStatus status) {
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.staff = staff;
        this.status = status;
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

    public UserRefDTO getManager() {
        return manager;
    }

    public void setManager(UserRefDTO manager) {
        this.manager = manager;
    }

    public List<UserRefDTO> getStaff() {
        return staff;
    }

    public void setStaff(List<UserRefDTO> staff) {
        this.staff = staff;
    }

    public EProjectStatus getStatus() {
        return status;
    }

    public void setStatus(EProjectStatus status) {
        this.status = status;
    }
}


