package com.ai.st.microservice.providers.dto;

import java.io.Serializable;
import java.util.List;

public class RequestPaginatedDto implements Serializable {

    private static final long serialVersionUID = -8704576480232726830L;

    private Integer number;
    private Integer numberOfElements;
    private Long totalElements;
    private Integer totalPages;
    private Integer size;
    private List<?> items;

    public RequestPaginatedDto() {

    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<?> getItems() {
        return items;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

}
