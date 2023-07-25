package by.it_academy.jd2.core.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRegistrationDTO {
    @NotEmpty
    @Size(max = 50)
    @Pattern(regexp = "^[^\\d]+$", message = "ФИО не должно содержать цифр")
    private String fio;
    @NotEmpty
    @Email(message = "Некорректный адрес электронной почты")
    private String mail;
    @NotEmpty
    @Size(min = 6, max = 30, message = "Пароль должен содержать от {min} до {max} символов")
    private String password;

    public UserRegistrationDTO() {
    }

    public UserRegistrationDTO(String mail, String fio, String password) {
        this.mail = mail;
        this.fio = fio;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
