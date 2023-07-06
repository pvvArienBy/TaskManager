package by.it_academy.jd2.core.enums;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum ERole {

    ADMIN,

    USER;

    @JsonEnumDefaultValue
    public static final ERole DEFAULT = USER;

}
