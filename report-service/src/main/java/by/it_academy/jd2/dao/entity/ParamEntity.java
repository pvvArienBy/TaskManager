package by.it_academy.jd2.dao.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "param")
public class ParamEntity implements Serializable {

    static final long serialVersionUID = 1L;
    @Id
    private UUID uuid;

    @JoinColumn( name = "user_uuid", nullable = false)
    private UUID userUuid;

    @Column(name = "from_date")
    private LocalDate fromDt;
    @Column(name = "too_date")
    private LocalDate tooDt;

    public ParamEntity() {
    }

    public ParamEntity(UUID uuid, UUID userUuid, LocalDate fromDt, LocalDate tooDt) {
        this.uuid = uuid;
        this.userUuid = userUuid;
        this.fromDt = fromDt;
        this.tooDt = tooDt;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID id) {
        this.uuid = id;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }

    public LocalDate getFromDt() {
        return fromDt;
    }

    public void setFromDt(LocalDate fromDt) {
        this.fromDt = fromDt;
    }

    public LocalDate getTooDt() {
        return tooDt;
    }

    public void setTooDt(LocalDate tooDt) {
        this.tooDt = tooDt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParamEntity that = (ParamEntity) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(userUuid, that.userUuid) && Objects.equals(fromDt, that.fromDt) && Objects.equals(tooDt, that.tooDt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, userUuid, fromDt, tooDt);
    }
}
