package com.mjc.school.service.interfaces;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface NewsMapper {

    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    NewsDtoResponse newsToNewsDtoResponse(NewsModel newsModel);

    @Mapping(target = "authorId", ignore = true)
    @Mapping(target = "tagId", ignore = true)
    @Mapping(target = "commentId", ignore = true)
    NewsDtoRequest newsToNewsDtoRequest(NewsModel newsModel);

    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "authorModel", ignore = true)
    @Mapping(target = "taggedNews", ignore = true)
    NewsModel newsDtoRequestToNews(NewsDtoRequest newsDtoRequest);

    List<NewsDtoResponse> listNewsToNewsDtoResponse(List<NewsModel> newsModelList);

}
