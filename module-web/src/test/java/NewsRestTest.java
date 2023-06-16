import com.mjc.school.service.dto.NewsDtoRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

public class NewsRestTest {
    private final static String BASE_URI = "http://localhost";
    private final static String BASE_PATH = "/news";
    private final static String PAGED_FIND_ALL = "?page=0&size=20&sortBy=title";
    private final static String READ_BY_NEWS_ID = "/2";
    private final static String CREATE_NEWS = "/create";
    private final static String PATCH_NEWS = "/10";
    private final static String DELETE_NEWS = "/7";


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
    public void findAllANewsControllerTest() {
//        Response response = given().contentType(ContentType.JSON)
//                .param("page", 0)
//                .param("size", 20)
//                .param("sortBy", "title")
//                .when().get(BASE_COMMENT_CONTROLLER_PATH + PAGED_FIND_ALL)
//                .then().extract().response();
//        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void readCNewsByIdControllerTest() {
//        Response response = given().contentType(ContentType.JSON)
//                .param("id", 2)
//                .when().get(BASE_COMMENT_CONTROLLER_PATH + READ_BY_NEWS_ID)
//                .then().extract().response();
//        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void createCNewsControllerTest() {
//        NewsDtoRequest request = new NewsDtoRequest(null, "title", "created news", 1L, 1L, 1L);
//        Response response = given().contentType("application/json")
//                .body(request)
//                .when().post(BASE_COMMENT_CONTROLLER_PATH + CREATE_NEWS)
//                .then()
//                .extract().response();
//        Assertions.assertEquals(201, response.statusCode());
//        Assertions.assertEquals("created news", response.jsonPath().getString("content"));
    }

    @Test
    public void patchNewsControllerTest() {
//        String requestBody = "[" +
//                "  {" +
//                "    \"op\": \"replace\"," +
//                "    \"path\": \"/content\"," +
//                "    \"value\": \"Patched news\"" +
//                "  }" +
//                "]";
//
//        Response response = given().contentType("application/json-patch+json")
//                .body(requestBody)
//                .when()
//                .patch(BASE_COMMENT_CONTROLLER_PATH + PATCH_NEWS)
//                .then()
//                .extract().response();
//
//        Assertions.assertEquals(200, response.statusCode());
//        Assertions.assertEquals("Patched news", response.jsonPath().getString("content"));
    }

    @Test
    public void deleteNewsControllerTest() {
//        Response response = given().when()
//                .delete(BASE_COMMENT_CONTROLLER_PATH + DELETE_NEWS).then().extract().response();
//        Assertions.assertEquals(204, response.statusCode());
    }
}
