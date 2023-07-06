package by.it_academy.jd2.core.enums;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum EStatusUser {

    PENDING_CONFIRMATION,

    ACTIVE,

    DELETED,

    BLOCKED;

    @JsonEnumDefaultValue
    public static final EStatusUser DEFAULT = ACTIVE;
}
