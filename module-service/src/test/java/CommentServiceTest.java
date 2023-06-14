import com.mjc.school.repository.implementation.CommentRepository;
import com.mjc.school.repository.implementation.NewsRepository;
import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import com.mjc.school.service.implementation.CommentService;
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
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private NewsRepository newsRepository;

    private NewsModel news;
    private CommentModel comment;
    private Long idComment;
    private Long idNews;
    private LocalDateTime now;

    @BeforeEach
    public void setUp() {
        idComment = 1L;
        idNews = 2L;
        now = LocalDateTime.now();
        news = new NewsModel(idNews, "Title", "Comment", now, now);
        comment = new CommentModel(idComment, "comment", now, now, news);
    }

    @Test
    public void findAllPagedCommentsTest() {
        Pageable pageable = Pageable.ofSize(1);
        Page<CommentModel> pagedComments = Mockito.mock(Page.class);
        Mockito.when(commentRepository.findAll(pageable)).thenReturn(pagedComments);
        commentService.findAll(pageable);
        Mockito.verify(commentRepository).findAll(pageable);
    }

    @Test
    public void readCommentByIdTest() {
        Mockito.when(commentRepository.readById(idComment)).thenReturn(Optional.ofNullable(comment));
        Mockito.when(commentRepository.existById(idComment)).thenReturn(true);
        CommentDtoResponse response = commentService.readById(idComment);
        Mockito.verify(commentRepository).readById(idComment);
        Assertions.assertEquals(comment.getContent(), response.getContent());
    }

    @Test
    public void readCommentByNewsIdTest() {
        Mockito.when(commentRepository.readCommentsByNewsId(idNews)).thenReturn(List.of(comment));
        List<CommentDtoResponse> responses = commentService.readCommentsByNewsId(idNews);
        Mockito.verify(commentRepository).readCommentsByNewsId(idNews);
        Assertions.assertEquals(comment.getContent(), responses.get(0).getContent());
    }

    @Test
    public void createCommentTest() {
        CommentModel createdComment = new CommentModel(idComment, "new comment", now, now, news);
        CommentDtoRequest commentToCreate = new CommentDtoRequest(idComment, "new comment", idNews);
        Mockito.when(commentRepository.create(any())).thenReturn(createdComment);
        CommentDtoResponse response = commentService.create(commentToCreate);
        Mockito.verify(commentRepository).create(createdComment);
        Assertions.assertEquals(commentToCreate.getContent(), response.getContent());
    }

    @Test
    public void updateCommentTest() {
        CommentModel updatedComment = new CommentModel(idComment, "update comment", now, now, news);
        CommentDtoRequest commentToUpdate = new CommentDtoRequest(idComment, "update comment", idNews);
        Mockito.when(commentRepository.existById(idComment)).thenReturn(true);
        Mockito.when(commentRepository.readById(idComment)).thenReturn(Optional.ofNullable(comment));
        Mockito.when(newsRepository.readById(idNews)).thenReturn(Optional.ofNullable(news));
        Mockito.when(newsRepository.existById(idNews)).thenReturn(true);
        Mockito.when(commentRepository.update(any())).thenReturn(updatedComment);
        CommentDtoResponse response = commentService.update(commentToUpdate);
        Mockito.verify(commentRepository).update(any());
        Assertions.assertEquals(commentToUpdate.getContent(), response.getContent());
    }

    @Test
    public void deleteCommentTest() {
        Mockito.when(commentRepository.existById(idComment)).thenReturn(true);
        Mockito.when(commentRepository.deleteById(idComment)).thenReturn(true);
        Mockito.when(commentRepository.readById(idComment)).thenReturn(Optional.ofNullable(comment));
        boolean response = commentService.deleteById(idComment);
        Assertions.assertTrue(response);
        Mockito.verify(commentRepository).deleteById(idComment);
    }
}
