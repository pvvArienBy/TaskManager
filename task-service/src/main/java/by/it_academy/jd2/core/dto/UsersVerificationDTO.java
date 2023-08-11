package by.it_academy.jd2.core.dto;

import java.util.List;
import java.util.UUID;

public class UsersVerificationDTO {
    private UUID manager;
    private List <UUID> staff;

    public UsersVerificationDTO() {
    }

    public UsersVerificationDTO(UUID manager, List<UUID> staff) {
        this.manager = manager;
        this.staff = staff;
    }

    public UUID getManager() {
        return manager;
    }

    public void setManager(UUID manager) {
        this.manager = manager;
    }

    public List<UUID> getStaff() {
        return staff;
    }

    public void setStaff(List<UUID> staff) {
        this.staff = staff;
    }
}
