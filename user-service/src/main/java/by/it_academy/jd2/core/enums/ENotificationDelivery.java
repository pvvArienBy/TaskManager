package by.it_academy.jd2.core.enums;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum ENotificationDelivery {

    MAIL,

    TELEGRAM;

    @JsonEnumDefaultValue
    public static final ENotificationDelivery DEFAULT = MAIL;
}
