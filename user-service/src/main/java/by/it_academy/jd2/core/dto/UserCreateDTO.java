package by.it_academy.jd2.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserCreateDTO {

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("mail")
    private String mail;

    @JsonProperty("password")
    private String password;

    @JsonProperty("telegram")
    private String telegram;

    @JsonProperty("position")
    private String position;

    @JsonProperty("role")
    private String role;

    @JsonProperty("status")
    private String status;

    @JsonProperty("notificationWay")
    private String notificationWay;

    public UserCreateDTO() {
    }

    public UserCreateDTO(String firstName, String lastName, String mail, String password, String telegram, String position, String role, String status, String notificationWay) {
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
}



