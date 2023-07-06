package by.it_academy.jd2.core.enums;

public enum ERole {

    ADMIN("admin"),

    USER("user");

    private final String value;

    ERole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    //todo check
    public static ERole fromValue(String value) {
        for (ERole role : ERole.values()) {
            if (role.value.equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid ERole value: " + value);
    }
}
