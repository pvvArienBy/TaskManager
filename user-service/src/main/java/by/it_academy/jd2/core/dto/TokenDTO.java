package by.it_academy.jd2.core.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDTO {
    private String token;

    public TokenDTO() {
    }

    public TokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
