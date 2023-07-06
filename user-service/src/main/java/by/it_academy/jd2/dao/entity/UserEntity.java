package by.it_academy.jd2.dao.entity;

import by.it_academy.jd2.core.enums.ENotificationDelivery;
import by.it_academy.jd2.core.enums.EPosition;
import by.it_academy.jd2.core.enums.ERole;
import by.it_academy.jd2.core.enums.EStatusUser;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    static final long serialVersionUID = 6L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    private String mail;

    private String password;

    private String telegram;

    @Enumerated(EnumType.STRING)
    private EPosition position;

    @Enumerated(EnumType.STRING)
    private ERole role;

    @Enumerated(EnumType.STRING)
    private EStatusUser status;

    @Column(name = "notification_way")
    @Enumerated(EnumType.STRING)
    private ENotificationDelivery notificationWay;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Version
    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    public UserEntity() {
    }

    public UserEntity(Long id, String firstName, String lastName, String mail, String password, String telegram, EPosition position, ERole role, EStatusUser status, ENotificationDelivery notificationWay, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.password = password;
        this.telegram = telegram;
        this.position = position;
        this.role = role;
        this.status = status;
        this.notificationWay = notificationWay;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public EPosition getPosition() {
        return position;
    }

    public void setPosition(EPosition position) {
        this.position = position;
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

    public ENotificationDelivery getNotificationWay() {
        return notificationWay;
    }

    public void setNotificationWay(ENotificationDelivery notificationWay) {
        this.notificationWay = notificationWay;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(mail, that.mail) && Objects.equals(password, that.password) && Objects.equals(telegram, that.telegram) && position == that.position && role == that.role && status == that.status && notificationWay == that.notificationWay && Objects.equals(createDate, that.createDate) && Objects.equals(updateDate, that.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, mail, password, telegram, position, role, status, notificationWay, createDate, updateDate);
    }
}

