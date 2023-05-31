package com.mjc.school.service.utils;

import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.dto.TagDtoResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;

public class TagPageConvertor implements Converter<TagModel, TagDtoResponse> {
    @Override
    public TagDtoResponse convert(TagModel source) {
        TagDtoResponse tagDtoResponse = new TagDtoResponse();
        tagDtoResponse.setId(source.getId());
        tagDtoResponse.setName(source.getName());
        return tagDtoResponse;
    }

    public Page<TagDtoResponse> tagToDtoResponses(Page<TagModel> tagModels) {
        return tagModels.map(this::convert);
    }
}
