package com.mjc.school.service.dto;

import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class TagDtoRequest extends RepresentationModel<TagDtoRequest> {

    @Min(1)
    @Max(Long.MAX_VALUE)
    private Long id;
    @NotNull
    @Size(min = 3, max = 15)
    private String name;

    public TagDtoRequest(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TagDtoRequest() {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagDtoRequest that = (TagDtoRequest) o;
        return id.equals(that.id) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "TagDtoRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
