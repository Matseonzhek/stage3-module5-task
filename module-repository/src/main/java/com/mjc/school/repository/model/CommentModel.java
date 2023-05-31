package com.mjc.school.repository.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comments")
public class CommentModel implements BaseEntity<Long>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String content;
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;
    @Column
    @LastModifiedDate
    private LocalDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private NewsModel newsModel;

    public CommentModel(Long id, String content, LocalDateTime createdDate, LocalDateTime updatedDate, NewsModel newsModel) {
        this.id = id;
        this.content = content;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.newsModel = newsModel;
    }

    public CommentModel() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public NewsModel getNewsModel() {
        return newsModel;
    }

    public void setNewsModel(NewsModel newsModel) {
        this.newsModel = newsModel;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentModel that = (CommentModel) o;
        return content.equals(that.content) && createdDate.equals(that.createdDate) && updatedDate.equals(that.updatedDate) && newsModel.equals(that.newsModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, createdDate, updatedDate, newsModel);
    }

    @Override
    public String toString() {
        return "CommentModel{" +
                "id=" + id +
                ", content='" + content +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", newsModel=" + newsModel +
                '}';
    }
}
