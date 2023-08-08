package by.it_academy.jd2.dao.entity;

import by.it_academy.jd2.core.enums.ERole;
import by.it_academy.jd2.core.enums.EStatusUser;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity implements Serializable, UserDetails {
    static final long serialVersionUID = 9L;
    @Id
    private UUID uuid;
    @CreationTimestamp
    @Column(name = "date_create", nullable = false)
    private LocalDateTime dtCreate;
    @Version
    @UpdateTimestamp
    @Column(name = "date_update",nullable = false)
    private LocalDateTime dtUpdate;
    @Email(message = "Некорректный адрес электронной почты")
    @Column(nullable = false)
    private String mail;
    @Size(max = 255)
    @Pattern(regexp = "^[^\\d]+$", message = "ФИО не должно содержать цифр")
    @Column(nullable = false)
    private String fio;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ERole role;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EStatusUser status;
    @Column(nullable = false)
    private String password;

    public UserEntity() {
    }

    public UserEntity(UUID uuid, LocalDateTime createDate, LocalDateTime updateDate,
                      String mail, String fio, ERole role, EStatusUser status, String password) {
        this.uuid = uuid;
        this.dtCreate = createDate;
        this.dtUpdate = updateDate;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
        this.password = password;
    }

    public UserEntity(LocalDateTime dtCreate, LocalDateTime dtUpdate,
                      String mail, String fio, ERole role, EStatusUser status, String password) {
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return mail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return (!status.equals(EStatusUser.DEACTIVATED));
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return (status.equals(EStatusUser.ACTIVATED));
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

