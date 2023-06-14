import com.mjc.school.repository.implementation.AuthorRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.implementation.AuthorService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @InjectMocks
    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    private AuthorModel author;
    private Long idAuthor;
    private LocalDateTime now;
    private Long idNews;


    @BeforeEach
    public void setUp() {
        idAuthor = 1L;
        idNews = 2L;
        now = LocalDateTime.now();
        author = new AuthorModel(idAuthor, "author", now, now);
    }

    @Test
    public void findAllPagedAuthorsTest() {

        List<AuthorModel> authorsList = new ArrayList<>();
        authorsList.add(author);
        Pageable pageable = Pageable.ofSize(authorsList.size());
        Page<AuthorModel> pagedAuthors = Mockito.mock(Page.class);
        Mockito.when(authorRepository.findAll(Mockito.any())).thenReturn(pagedAuthors);
        authorService.findAll(pageable);
        verify(authorRepository).findAll(pageable);

    }

    @Test
    public void readAuthorByIdTest() {

        when(authorRepository.readById(idAuthor)).thenReturn(Optional.of(author));
        when(authorRepository.existById(idAuthor)).thenReturn(true);
        AuthorDtoResponse authorDtoResponse = authorService.readById(idAuthor);
        verify(authorRepository).readById(idAuthor);
        Assertions.assertEquals(author.getName(), authorDtoResponse.getName());
    }

    @Test
    public void readAuthorByNewsIdTest() {
        when(authorRepository.readAuthorByNewsId(idNews)).thenReturn(Optional.ofNullable(author));
        AuthorDtoResponse authorDtoResponse = authorService.readAuthorByNewsId(idNews);
        verify(authorRepository, times(2)).readAuthorByNewsId(idNews);
        Assertions.assertEquals(author.getName(), authorDtoResponse.getName());
    }

    @Test
    public void createAuthorTest() {
        AuthorModel createdAuthor = new AuthorModel(idAuthor, "new author", now, now);
        AuthorDtoRequest authorToCreate = new AuthorDtoRequest(idAuthor, "new author");
        when(authorRepository.create(any())).thenReturn(createdAuthor);
        AuthorDtoResponse saved = authorService.create(authorToCreate);
        verify(authorRepository).create(any());
        Assertions.assertEquals(authorToCreate.getName(), saved.getName());
    }

    @Test
    public void updateAuthorTest() {
        AuthorModel updatedAuthor = new AuthorModel(idAuthor, "updated author", now, now);
        AuthorDtoRequest authorToUpdate = new AuthorDtoRequest(idAuthor, "updated author");
        when(authorRepository.update(any())).thenReturn(updatedAuthor);
        when(authorRepository.existById(idAuthor)).thenReturn(true);
        when(authorRepository.readById(anyLong())).thenReturn(Optional.ofNullable(author));
        AuthorDtoResponse updated = authorService.update(authorToUpdate);
        verify(authorRepository).update(any());
        Assertions.assertEquals(authorToUpdate.getName(), updated.getName());

    }

    @Test
    public void deleteAuthorByIdTest() {
        when(authorRepository.existById(idAuthor)).thenReturn(true);
        when(authorRepository.deleteById(idAuthor)).thenReturn(true);
        boolean response = authorService.deleteById(idAuthor);
        Assertions.assertTrue(response);
        verify(authorRepository).deleteById(idAuthor);
    }
}
