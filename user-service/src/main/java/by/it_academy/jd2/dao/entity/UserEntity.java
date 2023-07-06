package by.it_academy.jd2.dao.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    static final long serialVersionUID = 5L;

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

    private String position;

    private String role;

    private String status;

    @Column(name = "notification_way")
    private String notificationWay;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Version
    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    public UserEntity() {
    }

    public UserEntity(Long id, String firstName, String lastName, String mail, String password, String telegram, String position, String role, String status, String notificationWay, LocalDateTime createDate, LocalDateTime updateDate) {
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotificationWay() {
        return notificationWay;
    }

    public void setNotificationWay(String notificationWay) {
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
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(mail, that.mail) && Objects.equals(password, that.password) && Objects.equals(telegram, that.telegram) && Objects.equals(position, that.position) && Objects.equals(role, that.role) && Objects.equals(status, that.status) && Objects.equals(notificationWay, that.notificationWay) && Objects.equals(createDate, that.createDate) && Objects.equals(updateDate, that.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, mail, password, telegram, position, role, status, notificationWay, createDate, updateDate);
    }
}

