package by.it_academy.jd2.core.dto;

import by.it_academy.jd2.core.enums.EProjectStatus;

import java.util.List;
import java.util.UUID;

public class ProjectDTO {
    private UUID uuid;
    private Long dtCreate;
    private Long dtUpdate;
    private String name;
    private String description;
    private RefDTO manager;
    private List<RefDTO> staff;
    private EProjectStatus status;

    public ProjectDTO() {
    }

    public ProjectDTO(UUID uuid, Long dtCreate, Long dtUpdate,
                      String name, String description, RefDTO manager,
                      List<RefDTO> staff, EProjectStatus status) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.staff = staff;
        this.status = status;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Long getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Long dtCreate) {
        this.dtCreate = dtCreate;
    }

    public Long getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(Long dtUpdate) {
        this.dtUpdate = dtUpdate;
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

    public RefDTO getManager() {
        return manager;
    }

    public void setManager(RefDTO manager) {
        this.manager = manager;
    }

    public List<RefDTO> getStaff() {
        return staff;
    }

    public void setStaff(List<RefDTO> staff) {
        this.staff = staff;
    }

    public EProjectStatus getStatus() {
        return status;
    }

    public void setStatus(EProjectStatus status) {
        this.status = status;
    }
}
