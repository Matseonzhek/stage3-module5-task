package com.mjc.school.service.utils;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.dto.AuthorDtoResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;

public class AuthorPageConvert implements Converter<AuthorModel, AuthorDtoResponse> {
    @Override
    public AuthorDtoResponse convert(AuthorModel source) {
        AuthorDtoResponse authorDtoResponse = new AuthorDtoResponse();
        authorDtoResponse.setId(source.getId());
        authorDtoResponse.setName(source.getName());
        authorDtoResponse.setCreatedDate(source.getCreatedDate());
        authorDtoResponse.setUpdatedDate(source.getUpdatedDate());
        return authorDtoResponse;
    }

    public Page<AuthorDtoResponse> pageAuthorToAuthorDtoResponse(Page<AuthorModel> authorModelPage) {
        return authorModelPage.map(this::convert);
    }
}
