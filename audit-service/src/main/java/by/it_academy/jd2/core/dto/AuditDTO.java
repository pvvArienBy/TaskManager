package by.it_academy.jd2.core.dto;

import org.example.mylib.tm.itacademy.dto.UserCheckDTO;
import org.example.mylib.tm.itacademy.enums.EssenceType;

import java.util.UUID;

public class AuditDTO {
    private UUID uuid;
    private Long dtCreate;
    private UserCheckDTO user;
    private String text;
    private EssenceType type;
    private String id;

    public AuditDTO() {
    }

    public AuditDTO(UUID uuid, Long dtCreate, UserCheckDTO user, String text, EssenceType type, String id) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.user = user;
        this.text = text;
        this.type = type;
        this.id = id;
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

    public UserCheckDTO getUser() {
        return user;
    }

    public void setUser(UserCheckDTO user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public EssenceType getType() {
        return type;
    }

    public void setType(EssenceType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
