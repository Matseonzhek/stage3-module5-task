import com.mjc.school.service.dto.TagDtoRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

public class TagRestTest {
    private final static String BASE_URI = "http://localhost";
    private final static String BASE_PATH = "/tags";
    private final static String READ_BY_TAG_ID = "/1";
    private final static String READ_BY_NEWS_ID = "/news/1/tags";
    private final static String CREATE_TAG = "/create";
    private final static String PATCH_TAG = "/3";
    private final static String DELETE_TAG = "/4";


    @LocalServerPort
    private final static int port = 8081;
    private final static String BASE_TAG_CONTROLLER_PATH = BASE_URI + ":" + port + BASE_PATH;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
        RestAssured.basePath = BASE_PATH;
    }

    @Test
    public void findAllTagsControllerTest() {
        Response response = given().contentType(ContentType.JSON)
                .when().get(BASE_TAG_CONTROLLER_PATH)
                .then().extract().response();
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void readTagByIdControllerTest() {
        Response response = given().contentType(ContentType.JSON)
                .param("id", 1)
                .when().get(BASE_TAG_CONTROLLER_PATH + READ_BY_TAG_ID)
                .then().extract().response();
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void readTagByNewsIdControllerTest() {
        Response response = given().contentType(ContentType.JSON)
                .param("id", 1)
                .when().get(BASE_TAG_CONTROLLER_PATH + READ_BY_NEWS_ID)
                .then().extract().response();
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void createTagControllerTest() {
        TagDtoRequest request = new TagDtoRequest(null, "created tag");
        Response response = given().contentType("application/json")
                .body(request)
                .when().post(BASE_TAG_CONTROLLER_PATH + CREATE_TAG)
                .then()
                .extract().response();
        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertEquals("created tag", response.jsonPath().getString("name"));
    }

    @Test
    public void patchTagControllerTest() {
        String requestBody = "[" +
                "  {" +
                "    \"op\": \"replace\"," +
                "    \"path\": \"/name\"," +
                "    \"value\": \"Patched tag\"" +
                "  }" +
                "]";

        Response response = given().contentType("application/json-patch+json")
                .body(requestBody)
                .when()
                .patch(BASE_TAG_CONTROLLER_PATH + PATCH_TAG)
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Patched tag", response.jsonPath().getString("name"));
    }

    @Test
    public void deleteATagControllerTest() {
        Response response = given().when()
                .delete(BASE_TAG_CONTROLLER_PATH + DELETE_TAG).then().extract().response();
        Assertions.assertEquals(204, response.statusCode());
    }

}
