package by.it_academy.jd2.Mk_JD2_98_23.core.dto;

public class NotificationDTO {

    private Long id;

    private String to;

    private String text;

    private String  deliveryMethod;

    private Boolean readStatus;

    private Long version;

    public NotificationDTO() {
    }

    public NotificationDTO(Long id, String to, String text, String deliveryMethod, Boolean readStatus, Long version) {
        this.id = id;
        this.to = to;
        this.text = text;
        this.deliveryMethod = deliveryMethod;
        this.readStatus = readStatus;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public Boolean getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Boolean readStatus) {
        this.readStatus = readStatus;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
