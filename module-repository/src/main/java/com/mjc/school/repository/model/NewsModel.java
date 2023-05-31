package com.mjc.school.repository.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "news")
public class NewsModel implements BaseEntity<Long>, Serializable {

    @ManyToOne
    private AuthorModel authorModel;

    @OneToMany(mappedBy = "newsModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentModel> comments;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "news_tag",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<TagModel> taggedNews = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", unique = true)
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "createdDate", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createDate;
    @Column(name = "updatedDate", nullable = false)
    @LastModifiedDate
    private LocalDateTime updateDate;


    public NewsModel(Long id, String title, String content, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public NewsModel() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public AuthorModel getAuthorModel() {
        return authorModel;
    }

    public void setAuthorModel(AuthorModel authorModel) {
        this.authorModel = authorModel;
    }

    public List<TagModel> getTaggedNews() {
        return taggedNews;
    }

    public void setTaggedNews(List<TagModel> taggedNews) {
        this.taggedNews = taggedNews;
    }

    @Transactional
    public void addTag(TagModel tagModel) {
        taggedNews.add(tagModel);
        tagModel.getTags().add(this);
    }


    public void removeTag(TagModel tagModel) {
        taggedNews.remove(tagModel);
        tagModel.getTags().remove(this);
    }

    public void addComment(CommentModel commentModel) {
        comments.add(commentModel);
        commentModel.setNewsModel(this);
    }

    public void removeComment(CommentModel commentModel) {
        comments.remove(commentModel);
        commentModel.setNewsModel(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsModel newsModel = (NewsModel) o;
        return authorModel.equals(newsModel.authorModel) && id.equals(newsModel.id) && title.equals(newsModel.title) && content.equals(newsModel.content) && createDate.equals(newsModel.createDate) && updateDate.equals(newsModel.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorModel, id, title, content, createDate, updateDate);
    }
}
