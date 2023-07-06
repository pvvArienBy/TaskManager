package by.it_academy.jd2.core.enums;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum EStatusUser {

    PENDING_CONFIRMATION("pending configuration"),

    ACTIVE("active"),

    DELETED("deleted"),

    BLOCKED("blocked");

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
