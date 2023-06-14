import com.mjc.school.repository.implementation.AuthorRepository;
import com.mjc.school.repository.implementation.CommentRepository;
import com.mjc.school.repository.implementation.NewsRepository;
import com.mjc.school.repository.implementation.TagRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.implementation.NewsService;
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

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class NewsServiceTest {

    @InjectMocks
    private NewsService newsService;

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private CommentRepository commentRepository;

    private NewsModel news;
    private Long idNews;
    private Long idAuthor;
    private Long idTag;
    private Long idComment;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        idNews = 1L;
        idAuthor = 3L;
        idTag = 2L;
        idComment = 4L;
        now = LocalDateTime.now();
        news = new NewsModel(idNews, "News title", "News content", now, now);
    }

    @Test
    public void findAllPagedNewsTest() {
        Pageable pageable = Pageable.ofSize(1);
        Page<NewsModel> pagedNews = Mockito.mock(Page.class);
        Mockito.when(newsRepository.getPagedList(pageable)).thenReturn(pagedNews);

        newsService.findAll(pageable);

        Mockito.verify(newsRepository).getPagedList(pageable);
    }

    @Test
    public void readNewsByIdTest() {
        Mockito.when(newsRepository.readById(idNews)).thenReturn(Optional.ofNullable(news));
        Mockito.when(newsRepository.existById(idNews)).thenReturn(true);
        NewsDtoResponse newsDtoResponse = newsService.readById(idNews);
        Mockito.verify(newsRepository).readById(idNews);
        Assertions.assertEquals(news.getTitle(), newsDtoResponse.getTitle());
    }

    @Test
    public void createNewsTest() {
        NewsModel createdNews = new NewsModel(idNews, "Created title", "Created title", now, now);
        AuthorModel author = new AuthorModel(idAuthor, "author", now, now);
        TagModel tag = new TagModel(idTag, "tag");
        NewsDtoRequest newsToCreate = new NewsDtoRequest(idNews, "Created title", "Created title", idAuthor, idTag, idComment);
        Mockito.when(authorRepository.existById(idAuthor)).thenReturn(true);
        Mockito.when(tagRepository.existById(idTag)).thenReturn(true);
        Mockito.when(authorRepository.readById(idAuthor)).thenReturn(Optional.of(author));
        Mockito.when(tagRepository.readById(idTag)).thenReturn(Optional.of(tag));
        Mockito.when(newsRepository.create(any())).thenReturn(createdNews);
        NewsDtoResponse saved = newsService.create(newsToCreate);
        Mockito.verify(newsRepository).create(any());
        Assertions.assertEquals(newsToCreate.getTitle(), saved.getTitle());
    }

    @Test
    public void updatedNewTest() {
        NewsModel updatedNews = new NewsModel(idNews, "Updated title", "Updated title", now, now);
        AuthorModel author = new AuthorModel(idAuthor, "author", now, now);
        TagModel tag = new TagModel(idTag, "tag");
        CommentModel commentModel = new CommentModel(idComment, "comment", now, now, news);
        NewsDtoRequest newsToUpdate = new NewsDtoRequest(idNews, "Updated title", "Updated title", idAuthor, idTag, idComment);
        Mockito.when(authorRepository.existById(idAuthor)).thenReturn(true);
        Mockito.when(tagRepository.existById(idTag)).thenReturn(true);
        Mockito.when(newsRepository.existById(idNews)).thenReturn(true);
        Mockito.when(commentRepository.existById(idComment)).thenReturn(true);
        Mockito.when(authorRepository.readById(idAuthor)).thenReturn(Optional.of(author));
        Mockito.when(tagRepository.readById(idTag)).thenReturn(Optional.of(tag));
        Mockito.when(newsRepository.readById(idNews)).thenReturn(Optional.of(news));
        Mockito.when(commentRepository.readById(idComment)).thenReturn(Optional.of(commentModel));
        Mockito.when(newsRepository.update(any())).thenReturn(updatedNews);
        NewsDtoResponse updated = newsService.update(newsToUpdate);
        Mockito.verify(newsRepository).update(any());
        Assertions.assertEquals(newsToUpdate.getTitle(), updated.getTitle());
    }

    @Test
    public void deleteNewsTest() {
        Mockito.when(newsRepository.existById(idNews)).thenReturn(true);
        Mockito.when(newsRepository.deleteById(idNews)).thenReturn(true);
        boolean response = newsService.deleteById(idNews);
        Assertions.assertTrue(response);
        Mockito.verify(newsRepository).deleteById(idNews);
    }
}
