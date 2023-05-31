package com.mjc.school.service.interfaces;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "newsList", ignore = true)
    AuthorModel authorDtoRequestToAuthorModel(AuthorDtoRequest authorDtoRequest);

    @Mapping(target = "newsList", ignore = true)
    AuthorModel authorDtoResponseToAuthorModel(AuthorDtoResponse authorDtoResponse);

    AuthorDtoResponse authorModelToAuthorDtoResponse(AuthorModel authorModel);


    AuthorDtoRequest authorModelToAuthorDtoRequest(AuthorModel authorModel);

    List<AuthorDtoResponse> listAuthorToAuthorDtoResponse(List<AuthorModel> authorModelList);
}
