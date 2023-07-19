package by.it_academy.jd2.core.enums;

public enum EStatusUser {

    WAITING_ACTIVATION("waiting activation"),

    ACTIVATED ("activated"),

    DEACTIVATED ("deactivated");

    private final String value;

    EStatusUser(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    //todo check
    public static EStatusUser fromValue(String value) {
        for (EStatusUser role : EStatusUser.values()) {
            if (role.value.equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid EStatusUser value: " + value);
    }
}
