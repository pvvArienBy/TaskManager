package by.it_academy.jd2.core.enums;

public enum ENotificationDelivery {

    MAIL ("mail"),

    TELEGRAM("tg");

    private final String value;

    ENotificationDelivery(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    //todo check
    public static ENotificationDelivery fromValue(String value) {
        for (ENotificationDelivery role : ENotificationDelivery.values()) {
            if (role.value.equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid ENotificationDelivery value: " + value);
    }
}
