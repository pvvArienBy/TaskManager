package by.it_academy.jd2.core.enums;

public enum EPosition {

    PROJECT_MANAGER("project manager"),

    DEVELOPER("developer");

    private final String value;

    EPosition(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    //todo check
    public static EPosition fromValue(String value) {
        for (EPosition role : EPosition.values()) {
            if (role.value.equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid EPosition value: " + value);
    }
}
