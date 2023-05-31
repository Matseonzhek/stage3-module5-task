package com.mjc.school.service.implementation;


import com.mjc.school.repository.implementation.AuthorRepository;
import com.mjc.school.repository.implementation.CommentRepository;
import com.mjc.school.repository.implementation.NewsRepository;
import com.mjc.school.repository.implementation.TagRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.PageService;
import com.mjc.school.service.annotation.Validate;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.interfaces.NewsMapper;
import com.mjc.school.service.utils.NewsPageConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.mjc.school.service.exception.ServiceErrorCode.*;

@Service
public class
NewsService implements BaseService<NewsDtoRequest, NewsDtoResponse, Long>, PageService<NewsDtoRequest, NewsDtoResponse, Long> {

    private final NewsRepository newsRepository;
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository, AuthorRepository authorRepository, TagRepository tagRepository, CommentRepository commentRepository) {
        this.newsRepository = newsRepository;
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<NewsDtoResponse> readAll() {
        return NewsMapper.INSTANCE.listNewsToNewsDtoResponse(newsRepository.readAll());
    }

    @Override
    public Page<NewsDtoResponse> findAll(Pageable pageable) {
        Page<NewsModel> newsModelPage = newsRepository.getPagedList(pageable);
        return new NewsPageConvert().pageNewsToNewsDtoResponse(newsModelPage);
    }


    @Validate(value = "checkId")
    @Override
    public NewsDtoResponse readById(Long id) {
        if (newsRepository.existById(id)) {
            Optional<NewsModel> optionalNewsModel = newsRepository.readById(id);
            return NewsMapper.INSTANCE.newsToNewsDtoResponse(optionalNewsModel.get());
        } else {
            throw new NotFoundException(String.format(NEWS_ID_DOES_NOT_EXIST.getMessage(), id));
        }
    }

    @Override
    @Transactional
    @Validate(value = "checkNews")
    public NewsDtoResponse create(NewsDtoRequest createRequest) {
        if (authorRepository.existById(createRequest.getAuthorId())) {
            NewsModel newsModel = NewsMapper.INSTANCE.newsDtoRequestToNews(createRequest);
            if (tagRepository.existById(createRequest.getTagId())) {
                TagModel tagModel = tagRepository.readById(createRequest.getTagId()).get();
                newsModel.addTag(tagModel);
            } else {
                throw new NotFoundException(String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), createRequest.getTagId()));
            }
            AuthorModel authorModel = authorRepository.readById(createRequest.getAuthorId()).get();
            newsModel.setAuthorModel(authorModel);
            NewsModel createdNewsModel = newsRepository.create(newsModel);
            return NewsMapper.INSTANCE.newsToNewsDtoResponse(createdNewsModel);
        } else
            throw new NotFoundException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), createRequest.getAuthorId()));

    }

    @Transactional
    @Validate(value = "checkNews")
    @Override
    public NewsDtoResponse update(NewsDtoRequest updateRequest) {
        if (newsRepository.existById(updateRequest.getId())) {
            NewsModel newsModel = newsRepository.readById(updateRequest.getId()).get();
            if (authorRepository.existById(updateRequest.getAuthorId())) {
                AuthorModel authorModel = authorRepository.readById(updateRequest.getAuthorId()).get();
                newsModel.setTitle(updateRequest.getTitle());
                newsModel.setContent(updateRequest.getContent());
                newsModel.setAuthorModel(authorModel);
                if (tagRepository.existById(updateRequest.getTagId())) {
                    TagModel tagModel = tagRepository.readById(updateRequest.getTagId()).get();
                    newsModel.addTag(tagModel);
                }
                if (commentRepository.existById(updateRequest.getCommentId())) {
                    CommentModel commentModel = commentRepository.readById(updateRequest.getCommentId()).get();
                    newsModel.addComment(commentModel);
                }
                NewsModel updatedNewsModel = newsRepository.update(newsModel);
                return NewsMapper.INSTANCE.newsToNewsDtoResponse(updatedNewsModel);
            } else
                throw new NotFoundException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), updateRequest.getAuthorId()));
        } else {
            throw new NotFoundException(String.format(NEWS_ID_DOES_NOT_EXIST.getMessage(), updateRequest.getId()));
        }
    }

    @Transactional
    public NewsDtoResponse patch(NewsDtoRequest newsDtoRequest) {
        NewsModel newsModel = newsRepository.readById(newsDtoRequest.getId()).get();
        newsDtoRequest.setAuthorId(newsModel.getAuthorModel().getId());
        return update(newsDtoRequest);
    }

    @Transactional
    @Validate(value = "checkId")
    @Override
    public boolean deleteById(Long id) {
        if (newsRepository.existById(id)) {
            newsRepository.deleteById(id);
            return true;
        } else {
            throw new NotFoundException(String.format(NEWS_ID_DOES_NOT_EXIST.getMessage(), id));
        }
    }

    public List<NewsDtoResponse> getNewsByOption(String tagName, Long tagId, String authorName, String title, String content) {
        return NewsMapper.INSTANCE.listNewsToNewsDtoResponse
                (newsRepository.getNewsByOption(tagName, tagId, authorName, title, content));
    }


}
