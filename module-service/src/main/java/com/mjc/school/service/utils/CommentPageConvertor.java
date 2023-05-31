package com.mjc.school.service.utils;

import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.service.dto.CommentDtoResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;

public class CommentPageConvertor implements Converter<CommentModel, CommentDtoResponse> {
    @Override
    public CommentDtoResponse convert(CommentModel source) {
        CommentDtoResponse commentModel = new CommentDtoResponse();
        commentModel.setId(source.getId());
        commentModel.setContent(source.getContent());
        commentModel.setCreatedDate(source.getCreatedDate());
        commentModel.setUpdatedDate(source.getUpdatedDate());
        return commentModel;
    }

    public Page<CommentDtoResponse> pageToCommentDtoResponse(Page<CommentModel> commentModels) {
        return commentModels.map(this::convert);
    }
}
