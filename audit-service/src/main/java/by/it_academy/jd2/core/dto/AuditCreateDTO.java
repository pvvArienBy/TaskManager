package by.it_academy.jd2.core.dto;

import by.it_academy.jd2.core.enums.EssenceType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AuditCreateDTO {
    private UserDTO user;
    @NotBlank(message = "text не должен быть пустым")
    @Size(max = 255)
    private String text;
    @NotNull(message = "type не должен быть пустым")
    @Enumerated(EnumType.STRING)
    private EssenceType type;
    @NotBlank(message = "id не должен быть пустым")
    private String id;

    public AuditCreateDTO() {
    }

    public AuditCreateDTO(UserDTO user, String text, EssenceType type, String id) {
        this.user = user;
        this.text = text;
        this.type = type;
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public EssenceType getType() {
        return type;
    }

    public void setType(EssenceType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
