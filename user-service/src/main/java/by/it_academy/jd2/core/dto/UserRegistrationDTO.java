package by.it_academy.jd2.core.dto;

import by.it_academy.jd2.annotation.ValidEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRegistrationDTO {
    @NotBlank(message = "fio не должен быть пустым")
    @Size(max = 255)
    @Pattern(regexp = "^[^\\d]+$", message = "fio не должно содержать цифр")
    private String fio;
    @NotBlank(message = "mail не должен быть пустым")
    @Email(message = "Некорректный адрес электронной почты")
    @ValidEmail(message = "mail - ${validatedValue} уже существует! Выберите другой mail.")
    private String mail;
    @Size(min = 6, max = 255, message = "Пароль должен содержать от {min} до {max} символов")
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
