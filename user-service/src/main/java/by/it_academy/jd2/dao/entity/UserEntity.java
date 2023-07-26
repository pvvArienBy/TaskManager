package by.it_academy.jd2.dao.entity;

import by.it_academy.jd2.core.enums.ERole;
import by.it_academy.jd2.core.enums.EStatusUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @Column(name = "date_create")
    private LocalDateTime dtCreate;
    @Version
    @UpdateTimestamp
    @Column(name = "date_update")
    private LocalDateTime dtUpdate;
    @NotBlank (message = "mail не должен быть пустым")
    @Email(message = "Некорректный адрес электронной почты")
    @Column(unique = true)
    private String mail;
    @NotBlank (message = "fio не должен быть пустым")
    @Size(max = 50)
    @Pattern(regexp = "^[^\\d]+$", message = "ФИО не должно содержать цифр")
    private String fio;
    @Enumerated(EnumType.STRING)
    private ERole role;
    @Enumerated(EnumType.STRING)
    private EStatusUser status;
    @NotBlank
    @Size(min = 6, max = 30, message = "Пароль должен содержать от {min} до {max} символов")
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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

