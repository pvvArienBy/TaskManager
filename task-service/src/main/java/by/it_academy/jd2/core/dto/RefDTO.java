package by.it_academy.jd2.core.dto;

import java.util.UUID;

public class RefDTO {
    private UUID uuid;

    public RefDTO() {
    }

    public RefDTO(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
