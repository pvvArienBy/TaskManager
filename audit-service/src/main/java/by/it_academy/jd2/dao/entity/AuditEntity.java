package by.it_academy.jd2.dao.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.example.mylib.tm.itacademy.enums.ERole;
import org.example.mylib.tm.itacademy.enums.EssenceType;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "audits")
public class AuditEntity implements Serializable {
    static final long serialVersionUID = 1L;
    @Id
    private UUID uuid;
    @CreationTimestamp
    @Column(name = "date_create")
    private LocalDateTime dtCreate;
    private UUID userUuid;
    @Email(message = "Некорректный адрес электронной почты")
    private String userMail;
    @Size(max = 255)
    private String userFio;
    @Enumerated(EnumType.STRING)
    private ERole userRole;
    @Size(max = 255)
    private String text;
    @Enumerated(EnumType.STRING)
    private EssenceType type;
    private String id;

    public AuditEntity() {
    }

    public AuditEntity(UUID uuid, LocalDateTime dtCreate, UUID userUuid,
                       String userMail, String userFio, ERole userRole,
                       String text, EssenceType type, String id) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.userUuid = userUuid;
        this.userMail = userMail;
        this.userFio = userFio;
        this.userRole = userRole;
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

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserFio() {
        return userFio;
    }

    public void setUserFio(String userFio) {
        this.userFio = userFio;
    }

    public ERole getUserRole() {
        return userRole;
    }

    public void setUserRole(ERole userRole) {
        this.userRole = userRole;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditEntity that = (AuditEntity) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(dtCreate, that.dtCreate)
                && Objects.equals(userUuid, that.userUuid) && Objects.equals(userMail, that.userMail)
                && Objects.equals(userFio, that.userFio) && userRole == that.userRole
                && Objects.equals(text, that.text) && type == that.type && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, userUuid, userMail, userFio, userRole, text, type, id);
    }
}
