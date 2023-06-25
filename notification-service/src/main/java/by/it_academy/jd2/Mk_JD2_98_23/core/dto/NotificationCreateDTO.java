package by.it_academy.jd2.Mk_JD2_98_23.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationCreateDTO {

    @JsonProperty("to")
    private Long to;

    @JsonProperty("text")
    private String text;

    @JsonProperty("deliveryMethod")
    private String  deliveryMethod;

    @JsonProperty("readStatus")
    private Boolean  readStatus;

    public NotificationCreateDTO() {
    }

    public NotificationCreateDTO(Long to, String text, String deliveryMethod, Boolean readStatus) {
        this.to = to;
        this.text = text;
        this.deliveryMethod = deliveryMethod;
        this.readStatus = readStatus;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
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
}
