package by.it_academy.jd2.core.dto;

import java.util.List;

public class PageOfUserDTO {
    private Integer number;
    private Integer size;
    private Integer totalPages;
    private Long totalElements;
    private Boolean first;
    private Integer numberOfElements;
    private Boolean last;
    private List<UserDTO> content;

    public PageOfUserDTO() {
    }

    public PageOfUserDTO(Integer number, Integer size, Integer totalPages, Long totalElements, Boolean first, Integer numberOfElements, Boolean last, List<UserDTO> content) {
        this.number = number;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.first = first;
        this.numberOfElements = numberOfElements;
        this.last = last;
        this.content = content;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public List<UserDTO> getContent() {
        return content;
    }

    public void setContent(List<UserDTO> content) {
        this.content = content;
    }
}
