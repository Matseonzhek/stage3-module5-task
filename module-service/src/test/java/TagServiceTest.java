import com.mjc.school.repository.implementation.TagRepository;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.implementation.TagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @InjectMocks
    private TagService tagService;

    @Mock
    private TagRepository tagRepository;

    private TagModel tag;
    private Long idTag;
    private Long idNews;

    @BeforeEach
    public void setUp() {
        idTag = 1L;
        idNews = 2L;
        tag = new TagModel(idTag, "tag");
    }

    @Test
    public void findAllPagedTagsTest() {
        Pageable pageable = Pageable.ofSize(1);
        Page<TagModel> pagedTags = Mockito.mock(Page.class);
        Mockito.when(tagRepository.findAll(pageable)).thenReturn(pagedTags);
        tagService.findAll(pageable);
        Mockito.verify(tagRepository).findAll(pageable);
    }

    @Test
    public void readTagByIdTest() {
        Mockito.when(tagRepository.existById(idTag)).thenReturn(true);
        Mockito.when(tagRepository.readById(idTag)).thenReturn(Optional.ofNullable(tag));
        TagDtoResponse response = tagService.readById(idTag);
        Mockito.verify(tagRepository).readById(idTag);
        Assertions.assertEquals(tag.getName(), response.getName());
    }

    @Test
    public void readTagByNewsIdTest() {
        Mockito.when(tagRepository.readTagsByNewsId(idNews)).thenReturn(List.of(tag));
        List<TagDtoResponse> response = tagService.readTagsByNewsId(idNews);
        Mockito.verify(tagRepository).readTagsByNewsId(idNews);
        Assertions.assertEquals(tag.getName(), response.get(0).getName());
    }

    @Test
    public void createTagTest() {
        TagModel createdTag = new TagModel(idTag, "new tag");
        TagDtoRequest tagToCreate = new TagDtoRequest(idTag, "new tag");
        Mockito.when(tagRepository.create(createdTag)).thenReturn(createdTag);
        TagDtoResponse response = tagService.create(tagToCreate);
        Mockito.verify(tagRepository).create(createdTag);
        Assertions.assertEquals(response.getName(), createdTag.getName());
    }

    @Test
    public void updateTagTest() {
        TagModel updatedTag = new TagModel(idTag, "updated tag");
        TagDtoRequest tagToUpdate = new TagDtoRequest(idTag, "updated tag");
        Mockito.when(tagRepository.existById(idTag)).thenReturn(true);
        Mockito.when(tagRepository.readById(idTag)).thenReturn(Optional.ofNullable(tag));
        Mockito.when(tagRepository.update(updatedTag)).thenReturn(updatedTag);
        TagDtoResponse response = tagService.update(tagToUpdate);
        Mockito.verify(tagRepository).update(updatedTag);
        Assertions.assertEquals(response.getName(), updatedTag.getName());
    }

    @Test
    public void deleteTagTest(){
        Mockito.when(tagRepository.existById(idTag)).thenReturn(true);
        Mockito.when(tagRepository.deleteById(idTag)).thenReturn(true);
        boolean response = tagService.deleteById(idTag);
        Assertions.assertTrue(response);
        Mockito.verify(tagRepository).deleteById(idTag);
    }
}
