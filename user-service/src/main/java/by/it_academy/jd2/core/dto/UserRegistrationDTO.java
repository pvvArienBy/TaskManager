package by.it_academy.jd2.core.dto;

import by.it_academy.jd2.annotation.ValidEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRegistrationDTO {
    @NotBlank(message = "fio - must not be empty")
    @Size(max = 255)
    @Pattern(regexp = "^[^\\d]+$", message = "fio - must not contain numbers")
    private String fio;
    @NotBlank(message = "mail - must not be empty")
    @Email(message = "Incorrect email address")
    @ValidEmail( message = "mail - ${validatedValue} already exists! Choose another mail.")
    @Pattern(regexp = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$", message = "Invalid email format!")
    private String mail;
    @Size(min = 6, max = 255, message = "Password must contain between {min} and {max} characters")
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
