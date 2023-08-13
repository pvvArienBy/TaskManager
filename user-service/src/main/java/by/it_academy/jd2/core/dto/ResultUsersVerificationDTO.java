package by.it_academy.jd2.core.dto;

public class ResultUsersVerificationDTO {
    private boolean managerCheckRole;
    private boolean managerCheck;
    private boolean listUsersCheck;

    public ResultUsersVerificationDTO() {
    }

    public ResultUsersVerificationDTO(boolean managerCheckRole, boolean managerCheck, boolean listUsersCheck) {
        this.managerCheckRole = managerCheckRole;
        this.managerCheck = managerCheck;
        this.listUsersCheck = listUsersCheck;
    }

    public boolean isManagerCheckRole() {
        return managerCheckRole;
    }

    public void setManagerCheckRole(boolean managerCheckRole) {
        this.managerCheckRole = managerCheckRole;
    }

    public boolean isManagerCheck() {
        return managerCheck;
    }

    public void setManagerCheck(boolean managerCheck) {
        this.managerCheck = managerCheck;
    }

    public boolean isListUsersCheck() {
        return listUsersCheck;
    }

    public void setListUsersCheck(boolean listUsersCheck) {
        this.listUsersCheck = listUsersCheck;
    }
}
