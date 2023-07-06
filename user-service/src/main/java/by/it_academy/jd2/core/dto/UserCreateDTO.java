package by.it_academy.jd2.core.dto;

import by.it_academy.jd2.core.enums.ENotificationDelivery;
import by.it_academy.jd2.core.enums.EPosition;
import by.it_academy.jd2.core.enums.ERole;
import by.it_academy.jd2.core.enums.EStatusUser;

public class UserCreateDTO {

    private String firstName;

    private String lastName;

    private String mail;

    private String password;

    private String telegram;

    private EPosition position;

    private ERole role;

    private EStatusUser status;

    private ENotificationDelivery notificationWay;

    public UserCreateDTO() {
    }

    public UserCreateDTO(String firstName, String lastName, String mail, String password, String telegram, EPosition position, ERole role, EStatusUser status, ENotificationDelivery notificationWay) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.password = password;
        this.telegram = telegram;
        this.position = position;
        this.role = role;
        this.status = status;
        this.notificationWay = notificationWay;
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
}



