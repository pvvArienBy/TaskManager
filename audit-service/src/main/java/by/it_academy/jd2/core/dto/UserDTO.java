package by.it_academy.jd2.core.dto;

import by.it_academy.jd2.core.enums.ERole;
import by.it_academy.jd2.core.enums.EStatusUser;

import java.util.UUID;

public class UserDTO {
    private UUID uuid;
    private Long dtCreate;
    private Long dtUpdate;
    private String mail;
    private String fio;
    private ERole role;
    private EStatusUser status;

    public UserDTO() {
    }

    public UserDTO(UUID uuid, Long createDate, Long updateDate, String mail, String fio, ERole role, EStatusUser status) {
        this.uuid = uuid;
        this.dtCreate = createDate;
        this.dtUpdate = updateDate;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public EStatusUser getStatus() {
        return status;
    }

    public void setStatus(EStatusUser status) {
        this.status = status;
    }
}
