package by.it_academy.jd2.core.dto;


//todo need?
public class CoordinatesDTO {

    private Long id;
    private Long version;

    public CoordinatesDTO() {
    }

    public CoordinatesDTO(Long id, Long version) {
        this.id = id;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
