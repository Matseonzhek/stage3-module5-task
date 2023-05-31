package com.mjc.school.service.utils;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;

public class NewsPageConvert implements Converter<NewsModel, NewsDtoResponse> {
    @Override
    public NewsDtoResponse convert(NewsModel source) {
        NewsDtoResponse newsDtoResponse = new NewsDtoResponse();
        newsDtoResponse.setId(source.getId());
        newsDtoResponse.setTitle(source.getTitle());
        newsDtoResponse.setContent(source.getContent());
        newsDtoResponse.setCreateDate(source.getCreateDate());
        newsDtoResponse.setUpdateDate(source.getUpdateDate());
        return newsDtoResponse;
    }

   public Page<NewsDtoResponse> pageNewsToNewsDtoResponse(Page<NewsModel> newsModelPage) {
        return newsModelPage.map(this::convert);
    }
}
