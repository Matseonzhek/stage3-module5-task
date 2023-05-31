package com.mjc.school.service.interfaces;

import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TagMapper {

    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);


    TagDtoRequest tagToTagDtoRequest(TagModel tagModel);

    TagDtoResponse tagToTagDtoResponse(TagModel tagModel);

    @Mapping(target = "tags", ignore = true)
    TagModel tagDtoRequestToTag(TagDtoRequest tagDtoRequest);

    List<TagDtoResponse> listTagToTagDtoResponse(List<TagModel> tagModels);
}
