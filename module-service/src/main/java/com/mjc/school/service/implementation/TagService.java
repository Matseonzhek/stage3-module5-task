package com.mjc.school.service.implementation;

import com.mjc.school.repository.implementation.TagRepository;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.PageService;
import com.mjc.school.service.annotation.Validate;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.interfaces.TagMapper;
import com.mjc.school.service.utils.TagPageConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.mjc.school.service.exception.ServiceErrorCode.TAG_ID_DOES_NOT_EXIST;

@Service
public class TagService implements BaseService<TagDtoRequest, TagDtoResponse, Long>, PageService<TagDtoRequest, TagDtoResponse, Long> {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }


    @Override
    public List<TagDtoResponse> readAll() {
        return TagMapper.INSTANCE.listTagToTagDtoResponse(tagRepository.readAll());
    }

    @Override
    public Page<TagDtoResponse> findAll(Pageable pageable) {
        return new TagPageConvertor().tagToDtoResponses(tagRepository.findAll(pageable));
    }

    @Validate(value = "checkId")
    @Override
    public TagDtoResponse readById(Long id) {
        if (tagRepository.existById(id)) {
            return TagMapper.INSTANCE.tagToTagDtoResponse(tagRepository.readById(id).get());
        } else throw new NotFoundException(String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), id));
    }

    @Validate(value = "checkId")
    public List<TagDtoResponse> readTagsByNewsId(Long newsId) {
        return TagMapper.INSTANCE.listTagToTagDtoResponse(tagRepository.readTagsByNewsId(newsId));
    }

    @Validate(value = "checkTag")
    @Transactional
    @Override
    public TagDtoResponse create(TagDtoRequest createRequest) {
        return TagMapper.INSTANCE.tagToTagDtoResponse(tagRepository.create(TagMapper.INSTANCE.tagDtoRequestToTag(createRequest)));
    }

    @Validate(value = "checkTag")
    @Transactional
    @Override
    public TagDtoResponse update(TagDtoRequest updateRequest) {
        if (tagRepository.existById(updateRequest.getId())) {
            TagModel tagModel = tagRepository.readById(updateRequest.getId()).get();
            tagModel.setName(updateRequest.getName());
            TagModel updatedTagModel = tagRepository.update(tagModel);
            return TagMapper.INSTANCE.tagToTagDtoResponse(updatedTagModel);
        } else throw new NotFoundException(String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), updateRequest.getId()));
    }

    @Validate(value = "checkId")
    @Transactional
    @Override
    public boolean deleteById(Long id) {
        if (tagRepository.existById(id)) {
            tagRepository.deleteById(id);
            return true;
        } else throw new NotFoundException(String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), id));
    }
}
