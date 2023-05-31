package com.mjc.school.service.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Component
public class NewsDtoRequest extends RepresentationModel<NewsDtoRequest> {

    @Min(1)
    @Max(Long.MAX_VALUE)
    private Long id;
    @NotNull
    @Size(min = 5, max = 30)
    private String title;
    @NotNull
    @Size(min = 5, max = 255)
    private String content;
    private long authorId;
    private long tagId;
    private long commentId;

    public NewsDtoRequest() {
    }

    public NewsDtoRequest(Long id, String title, String content, long authorId, long tagId, long commentId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.tagId = tagId;
        this.commentId = commentId;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return "NewsDtoRequest{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", authorId=" + authorId +
                ", tagId=" + tagId +
                ", commentId=" + commentId +
                '}';
    }
}
