package com.mjc.school.service.implementation;

import com.mjc.school.repository.implementation.CommentRepository;
import com.mjc.school.repository.implementation.NewsRepository;
import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.PageService;
import com.mjc.school.service.annotation.Validate;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.interfaces.CommentMapper;
import com.mjc.school.service.utils.CommentPageConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.mjc.school.service.exception.ServiceErrorCode.COMMENT_ID_DOES_NOT_EXIST;
import static com.mjc.school.service.exception.ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST;

@Service
public class CommentService implements BaseService<CommentDtoRequest, CommentDtoResponse, Long>, PageService<CommentDtoRequest, CommentDtoResponse, Long> {

    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(NewsRepository newsRepository, CommentRepository commentRepository) {
        this.newsRepository = newsRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Page<CommentDtoResponse> findAll(Pageable pageable) {
        return new CommentPageConvertor().pageToCommentDtoResponse(commentRepository.findAll(pageable));
    }

    @Override
    public List<CommentDtoResponse> readAll() {
        return CommentMapper.INSTANCE.listCommentToCommentDtoResponse(commentRepository.readAll());
    }

    @Transactional
    @Validate(value = "checkId")
    @Override
    public CommentDtoResponse readById(Long id) {
        if (commentRepository.existById(id)) {
            CommentModel commentModel = commentRepository.readById(id).get();
            return CommentMapper.INSTANCE.commentToCommentDtoResponse(commentModel);
        } else throw new NotFoundException(String.format(COMMENT_ID_DOES_NOT_EXIST.getMessage(), id));
    }

    @Transactional
    @Validate(value = "checkComment")
    public List<CommentDtoResponse> readCommentsByNewsId(Long newsId) {
        return CommentMapper.INSTANCE.listCommentToCommentDtoResponse(commentRepository.readCommentsByNewsId(newsId));
    }

    @Transactional
    @Validate(value = "checkComment")
    @Override
    public CommentDtoResponse create(CommentDtoRequest createRequest) {
        if (newsRepository.existById(createRequest.getNewsModelId())) {
            CommentModel commentModel = CommentMapper.INSTANCE.commentDtoRequestToCommentModel(createRequest);
            NewsModel newsModel = newsRepository.readById(createRequest.getNewsModelId()).get();
            commentModel.setNewsModel(newsModel);
            commentModel.setContent(createRequest.getContent());
            CommentModel createdCommentModel = commentRepository.create(commentModel);
            newsModel.addComment(createdCommentModel);
            return CommentMapper.INSTANCE.commentToCommentDtoResponse(createdCommentModel);
        } else
            throw new NotFoundException(String.format(NEWS_ID_DOES_NOT_EXIST.getMessage(), createRequest.getNewsModelId()));
    }

    @Transactional
    @Validate(value = "checkComment")
    @Override
    public CommentDtoResponse update(CommentDtoRequest updateRequest) {
        if (newsRepository.existById(updateRequest.getNewsModelId())) {
            NewsModel newsModel = newsRepository.readById(updateRequest.getNewsModelId()).get();
            if (commentRepository.existById(updateRequest.getId())) {
                CommentModel commentModel = commentRepository.readById(updateRequest.getId()).get();
                commentModel.setContent(updateRequest.getContent());
                commentModel.setNewsModel(newsModel);
                CommentModel updatedCommentModel = commentRepository.update(commentModel);
                return CommentMapper.INSTANCE.commentToCommentDtoResponse(updatedCommentModel);
            } else
                throw new NotFoundException(String.format(COMMENT_ID_DOES_NOT_EXIST.getMessage(), updateRequest.getId()));
        } else
            throw new NotFoundException(String.format(NEWS_ID_DOES_NOT_EXIST.getMessage(), updateRequest.getNewsModelId()));
    }

    @Transactional
    @Validate(value = "checkComment")
    public CommentDtoResponse patch(CommentDtoRequest commentDtoRequest) {
        CommentModel commentModel = commentRepository.readById(commentDtoRequest.getId()).get();
        NewsModel newsModel = commentModel.getNewsModel();
        commentDtoRequest.setNewsModelId(newsModel.getId());
        return update(commentDtoRequest);
    }

    @Transactional
    @Validate(value = "checkId")
    @Override
    public boolean deleteById(Long id) {
        if (commentRepository.existById(id)) {
            CommentModel commentModel = commentRepository.readById(id).get();
            NewsModel newsModel = commentModel.getNewsModel();
            newsModel.removeComment(commentModel);
            commentRepository.deleteById(id);
            return true;
        } else throw new NotFoundException(String.format(COMMENT_ID_DOES_NOT_EXIST.getMessage(), id));
    }
}
