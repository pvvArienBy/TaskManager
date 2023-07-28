package by.it_academy.jd2.core.dto;

import by.it_academy.jd2.core.enums.ERole;
import by.it_academy.jd2.core.enums.EStatusUser;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

public class UserCreateUpdateDTO {
    @NotBlank(message = "mail не должен быть пустым")
    @Email(message = "Некорректный адрес электронной почты")
    private String mail;
    @NotBlank(message = "fio не должен быть пустым")
    @Size(max = 255)
    @Pattern(regexp = "^[^\\d]+$", message = "fio не должно содержать цифр")
    private String fio;
    @NotNull(message = "role не должен быть пустым")
    @Enumerated(EnumType.STRING)
    private ERole role;
    @NotNull(message = "status не должен быть пустым")
    @Enumerated(EnumType.STRING)
    private EStatusUser status;
    @NotBlank(message = "password не должен быть пустым")
    @Size(min = 6, max = 30, message = "Пароль должен содержать от {min} до {max} символов")
    private String password;

    public UserCreateUpdateDTO() {
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
}


