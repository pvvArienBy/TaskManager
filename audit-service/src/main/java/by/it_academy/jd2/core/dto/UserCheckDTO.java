package by.it_academy.jd2.core.dto;

import by.it_academy.jd2.core.enums.ERole;

import java.util.UUID;

public class UserCheckDTO {
    private UUID uuid;
    private String mail;
    private String fio;
    private ERole role;

    public UserCheckDTO() {
    }

    public UserCheckDTO(UUID uuid, String mail, String fio, ERole role) {
        this.uuid = uuid;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
}
