package by.it_academy.jd2.core.dto;

import java.util.UUID;

public class ProjectRefDTO {
    private UUID uuid;

    public ProjectRefDTO() {
    }

    public ProjectRefDTO(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
