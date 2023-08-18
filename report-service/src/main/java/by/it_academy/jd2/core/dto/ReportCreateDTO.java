package by.it_academy.jd2.core.dto;

import by.it_academy.jd2.core.enums.EType;

import java.util.Map;

public class ReportCreateDTO {
    private EType type;
    private Map<String, Object> params;

    public ReportCreateDTO() {
    }

    public ReportCreateDTO(EType type, Map<String, Object> params) {
        this.type = type;
        this.params = params;
    }

    public EType getType() {
        return type;
    }

    public void setType(EType type) {
        this.type = type;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
