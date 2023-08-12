package by.it_academy.jd2.core.dto;

import by.it_academy.jd2.annotation.ValidEmail;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.example.mylib.tm.itacademy.enums.ERole;
import org.example.mylib.tm.itacademy.enums.EStatusUser;

public class UserCreateUpdateDTO {
    @NotBlank(message = "mail - must not be empty")
    @Pattern(regexp = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$",
            message = "Invalid email address!")
    @ValidEmail(message = "mail - ${validatedValue} already exists! Choose another mail.")
    private String mail;
    @NotBlank(message = "fio - must not be empty")
    @Size(max = 255)
    @Pattern(regexp = "^[^\\d]+$", message = "fio - must not be empty")
    private String fio;
    @NotNull(message = "role - must not be empty")
    @Enumerated(EnumType.STRING)
    private ERole role;
    @NotNull(message = "status - must not be empty")
    @Enumerated(EnumType.STRING)
    private EStatusUser status;
    @NotBlank(message = "password - must not be empty")
    @Size(min = 6, max = 30, message = "Password must contain between {min} and {max} characters")
    private String password;

    public UserCreateUpdateDTO() {
    }

    public UserCreateUpdateDTO(String mail, String fio, ERole role, EStatusUser status, String password) {
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
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



