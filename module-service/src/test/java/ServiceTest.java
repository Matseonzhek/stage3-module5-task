//import com.mjc.school.repository.model.AuthorModel;
//import com.mjc.school.repository.model.NewsModel;
//import com.mjc.school.service.dto.AuthorDtoResponse;
//import com.mjc.school.service.dto.NewsDtoResponse;
//import com.mjc.school.service.implementation.AuthorService;
//import com.mjc.school.service.implementation.NewsService;
//import com.mjc.school.service.interfaces.AuthorMapper;
//import com.mjc.school.service.interfaces.NewsMapper;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
//
//import java.util.List;
//
//@SpringJUnitConfig
//public class ServiceTest {
//
//    private static final List<NewsModel> newsSource = DataSource.getDataSource().getNewsModelList();
//    private static final List<AuthorModel> authorSource = DataSource.getDataSource().getAuthorModelList();
//
//    @Autowired
//    AuthorService authorService;
//
//    @Autowired
//    private NewsService newsService;
//
//    @Test
//    public void getAllAuthorsTest() {
//        List<AuthorDtoResponse> authorsList = authorService.readAll();
//        Assertions.assertEquals(authorSource.size(), authorsList.size());
//    }
//
//    @Test
//    public void creatAuthorTest() {
//        AuthorModel authorModel = new AuthorModel(null, "Author", null, null);
//        authorService.create(AuthorMapper.INSTANCE.authorModelToAuthorDtoRequest(authorModel));
//        AuthorModel createdAuthorModel = authorSource.get(authorSource.size() - 1);
//        Assertions.assertEquals(authorModel.getName(), createdAuthorModel.getName());
//    }
//
//    @Test
//    public void updateAuthorTest() {
//        long updatedId = 3;
//        AuthorModel authorModel = new AuthorModel(updatedId, "Author", null, null);
//        authorService.update(AuthorMapper.INSTANCE.authorModelToAuthorDtoRequest(authorModel));
//        AuthorModel updatedAuthor = authorSource.get((int) updatedId - 1);
//        Assertions.assertEquals(authorModel.getName(), updatedAuthor.getName());
//    }
//
//    @Test
//    public void getAuthorByIdTest() {
//        long id = 1;
//        AuthorDtoResponse authorDtoRequest = authorService.readById(id);
//        Assertions.assertEquals("William Shakespeare", authorDtoRequest.getName());
//    }
//
//    @Test
//    void getAllNewsTest() {
//        List<NewsDtoResponse> newsDtoRequests = newsService.readAll();
//        Assertions.assertEquals(newsSource.size(), newsDtoRequests.size());
//    }
//
//
//    @Test
//    void createNewsTest() {
//        NewsModel newsModel = new NewsModel(null, "Title", "Content", null, null);
//        int databaseSizeBeforeSave = newsSource.size();
//        newsService.create(NewsMapper.INSTANCE.newsToNewsDtoRequest(newsModel));
//        int databaseSizeAfterSave = newsSource.size();
//        Assertions.assertEquals(1, databaseSizeAfterSave - databaseSizeBeforeSave);
//    }
//
//    @Test
//    void updateNewsTest() {
//        NewsModel newsModel = new NewsModel(5L, "Title", "Content", null, null);
//        long id = 5;
//        newsService.update(NewsMapper.INSTANCE.newsToNewsDtoRequest(newsModel));
//        NewsModel newsModelUpdated = newsSource.get((int) (id - 1));
//        Assertions.assertEquals(newsModel.getTitle(), newsModelUpdated.getTitle());
//    }
//
//    @Test
//    void getNewsByIdTest() {
//        long id = 1;
//        NewsDtoResponse newsDtoResponse = newsService.readById(id);
//        Assertions.assertEquals("GENERAL PROVISIONS", newsDtoResponse.getTitle());
//    }
//
//    @Configuration
//    @ComponentScan(basePackages = {"com.mjc.school.service", "com.mjc.school.repository"})
//    static class TestConfig {
//    }
//
//}
