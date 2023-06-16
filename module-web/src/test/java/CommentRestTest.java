import com.mjc.school.service.dto.CommentDtoRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

public class CommentRestTest {

    private final static String BASE_URI = "http://localhost";
    private final static String BASE_PATH = "/comments";
    private final static String PAGED_FIND_ALL = "?page=0&size=5&sortBy=content";
    private final static String READ_BY_COMMENT_ID = "/1";
    private final static String READ_BY_NEWS_ID = "/news/1/comments";
    private final static String CREATE_COMMENT = "/create";
    private final static String PATCH_COMMENT = "/1";
    private final static String DELETE_COMMENT = "/3";


    @LocalServerPort
    private final static int port = 8081;
    private final static String BASE_COMMENT_CONTROLLER_PATH = BASE_URI + ":" + port + BASE_PATH;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
        RestAssured.basePath = BASE_PATH;
    }

    @Test
    public void findAllACommentsControllerTest() {
//        Response response = given().contentType(ContentType.JSON)
//                .param("page", 0)
//                .param("size", 5)
//                .param("sortBy", "content")
//                .when().get(BASE_COMMENT_CONTROLLER_PATH + PAGED_FIND_ALL)
//                .then().extract().response();
//        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void readCommentByIdControllerTest() {
//        Response response = given().contentType(ContentType.JSON)
//                .param("id", 1)
//                .when().get(BASE_COMMENT_CONTROLLER_PATH + READ_BY_COMMENT_ID)
//                .then().extract().response();
//        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void readCommentByNewsIdControllerTest() {
//        Response response = given().contentType(ContentType.JSON)
//                .param("id", 1)
//                .when().get(BASE_COMMENT_CONTROLLER_PATH + READ_BY_NEWS_ID)
//                .then().extract().response();
//        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void createCommentControllerTest() {
//        CommentDtoRequest request = new CommentDtoRequest(null, "comment", 1L);
//        Response response = given().contentType("application/json")
//                .body(request)
//                .when().post(BASE_COMMENT_CONTROLLER_PATH + CREATE_COMMENT)
//                .then()
//                .extract().response();
//        Assertions.assertEquals(201, response.statusCode());
//        Assertions.assertEquals("comment", response.jsonPath().getString("content"));
    }

    @Test
    public void patchCommentControllerTest() {
//        String requestBody = "[" +
//                "  {" +
//                "    \"op\": \"replace\"," +
//                "    \"path\": \"/content\"," +
//                "    \"value\": \"Patched content\"" +
//                "  }" +
//                "]";
//
//        Response response = given().contentType("application/json-patch+json")
//                .body(requestBody)
//                .when()
//                .patch(BASE_COMMENT_CONTROLLER_PATH + PATCH_COMMENT)
//                .then()
//                .extract().response();
//
//        Assertions.assertEquals(200, response.statusCode());
//        Assertions.assertEquals("Patched content", response.jsonPath().getString("content"));
    }

    @Test
    public void deleteCommentControllerTest() {
//        Response response = given().when()
//                .delete(BASE_COMMENT_CONTROLLER_PATH + DELETE_COMMENT).then().extract().response();
//        Assertions.assertEquals(204, response.statusCode());
    }
}
