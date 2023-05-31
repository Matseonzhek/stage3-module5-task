package com.mjc.school.service.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Component
public class AuthorDtoRequest extends RepresentationModel<AuthorDtoResponse> {

    @Min(1)
    @Max(Long.MAX_VALUE)
    private Long id;
    @NotNull
    @Size(min = 3, max = 10)
    private String name;

    public AuthorDtoRequest() {
    }

    public AuthorDtoRequest(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AuthorDtoRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
