import com.mjc.school.service.dto.AuthorDtoRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

public class AuthorRestTest {

    private final static String BASE_URI = "http://localhost";
    private final static String BASE_PATH = "/authors";
    private final static String PAGED_FIND_ALL = "?page=1&size=20&sortBy=id";
    private final static String READ_BY_AUTHOR_ID = "/1";
    private final static String READ_BY_NEWS_ID = "/news/2/authors";
    private final static String CREATE_AUTHOR = "/create";
    private final static String PATCH_AUTHOR = "/1";
    private final static String DELETE_AUTHOR = "/3";


    @LocalServerPort
    private final static int port = 8081;
    private final static String BASE_AUTHOR_CONTROLLER_PATH = BASE_URI + ":" + port + BASE_PATH;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
        RestAssured.basePath = BASE_PATH;
    }



    @Test
    public void findAllAuthorsControllerTest() {
        Response response = given().contentType(ContentType.JSON)
                .param("page", 1)
                .param("size", 20)
                .param("sortBy", "id")
                .when().get(BASE_AUTHOR_CONTROLLER_PATH + PAGED_FIND_ALL)
                .then().extract().response();
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void readAuthorByIdControllerTest() {
        Response response = given().contentType(ContentType.JSON)
                .param("id", 1)
                .when().get(BASE_AUTHOR_CONTROLLER_PATH + READ_BY_AUTHOR_ID)
                .then().extract().response();
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void readAuthorByNewsIdControllerTest() {
        Response response = given().contentType(ContentType.JSON)
                .param("id", 2)
                .when().get(BASE_AUTHOR_CONTROLLER_PATH + READ_BY_NEWS_ID)
                .then().extract().response();
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void createAuthorControllerTest() {
        AuthorDtoRequest request = new AuthorDtoRequest(null,"Matseonzhek");
        Response response = given().contentType("application/json")
                .body(request)
                .when().post(BASE_AUTHOR_CONTROLLER_PATH + CREATE_AUTHOR)
                .then()
                .extract().response();
        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertEquals("Matseonzhek", response.jsonPath().getString("name"));
    }

    @Test
    public void patchAuthorControllerTest() {
        String requestBody = "[" +
                "  {" +
                "    \"op\": \"replace\"," +
                "    \"path\": \"/name\"," +
                "    \"value\": \"Matseonzhek\"" +
                "  }" +
                "]";

        Response response = given().contentType("application/json-patch+json")
                .body(requestBody)
                .when()
                .patch(BASE_AUTHOR_CONTROLLER_PATH + PATCH_AUTHOR)
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Matseonzhek", response.jsonPath().getString("name"));
    }

    @Test
    public void deleteAuthorControllerTest() {
        Response response = given().when()
                .delete(BASE_AUTHOR_CONTROLLER_PATH + DELETE_AUTHOR).then().extract().response();
        Assertions.assertEquals(204, response.statusCode());
    }
}
