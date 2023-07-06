package by.it_academy.jd2.core.enums;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum EPosition {

    PROJECT_MANAGER,

    DEVELOPER;

    @JsonEnumDefaultValue
    public static final EPosition DEFAULT = DEVELOPER;

}
