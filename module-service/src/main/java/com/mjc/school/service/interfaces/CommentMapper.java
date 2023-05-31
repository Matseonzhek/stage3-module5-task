package com.mjc.school.service.interfaces;

import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "newsModelId", ignore = true)
    CommentDtoRequest commentToCommentDtoRequest(CommentModel commentModel);

    CommentDtoResponse commentToCommentDtoResponse(CommentModel commentModel);

    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "newsModel", ignore = true)
    CommentModel commentDtoRequestToCommentModel(CommentDtoRequest commentDtoRequest);

    List<CommentDtoResponse> listCommentToCommentDtoResponse(List<CommentModel> comments);
}
