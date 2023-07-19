package by.it_academy.jd2.dao.entity;

import by.it_academy.jd2.core.enums.ERole;
import by.it_academy.jd2.core.enums.EStatusUser;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    static final long serialVersionUID = 7L;

    @Id
    private UUID uuid;

    @CreationTimestamp
    @Column(name = "date_create")
    private LocalDateTime dtCreate;

    @Version
    @UpdateTimestamp
    @Column(name = "date_update")
    private LocalDateTime dtUpdate;

    private String mail;

    private String fio;

    @Enumerated(EnumType.STRING)
    private ERole role;

    @Enumerated(EnumType.STRING)
    private EStatusUser status;

    private String password;

    public UserEntity() {
    }

    public UserEntity(UUID uuid, LocalDateTime createDate, LocalDateTime updateDate, String mail, String fio, ERole role, EStatusUser status, String password) {
        this.uuid = uuid;
        this.dtCreate = createDate;
        this.dtUpdate = updateDate;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
        this.password = password;
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

    public void setDtCreate(LocalDateTime createDate) {
        this.dtCreate = createDate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime updateDate) {
        this.dtUpdate = updateDate;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(dtCreate, that.dtCreate) && Objects.equals(dtUpdate, that.dtUpdate) && Objects.equals(mail, that.mail) && Objects.equals(fio, that.fio) && role == that.role && status == that.status && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, mail, fio, role, status, password);
    }
}

