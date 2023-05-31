package com.mjc.school.service.dto;

import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class CommentDtoRequest extends RepresentationModel<CommentDtoRequest> {

    @Min(1)
    @Max((Long.MAX_VALUE))
    private Long id;
    @Size(min = 5, max = 255)
    @NotNull
    private String content;
    private Long newsModelId;

    public CommentDtoRequest(Long id, String content, Long newsModelId) {
        this.id = id;
        this.content = content;
        this.newsModelId = newsModelId;
    }

    public CommentDtoRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getNewsModelId() {
        return newsModelId;
    }

    public void setNewsModelId(Long newsModelId) {
        this.newsModelId = newsModelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentDtoRequest that = (CommentDtoRequest) o;
        return id.equals(that.id) && content.equals(that.content) && newsModelId.equals(that.newsModelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, newsModelId);
    }

    @Override
    public String toString() {
        return "CommentDtoRequest{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", newsModelId=" + newsModelId +
                '}';
    }
}
