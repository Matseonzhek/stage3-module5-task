import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class AuthorRestTest {

//    private final static String BASE_URI = "http://localhost";
//    private final static String BASE_PATH = "/author";
//
//    //    @LocalServerPort
//    private final static int port = 8080;
//
//    @BeforeAll
//    public static void setUp() {
//        RestAssured.baseURI = BASE_URI;
//        RestAssured.port = port;
//        RestAssured.basePath = BASE_PATH;
//    }


    @BeforeAll
    public static void setup() {
        String port = System.getProperty("server.port");
        if (port == null) {
            RestAssured.port = 8080;
        } else {
            RestAssured.port = Integer.parseInt(port);
        }


        String basePath = System.getProperty("server.base");
        if (basePath == null) {
            basePath = "/authors";
        }
        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if (baseHost == null) {
            baseHost = "http://localhost";
        }
        RestAssured.baseURI = baseHost;
    }

    @Test
    public void findAllTest() {
        Response response = given().contentType(ContentType.JSON)
                .param("page", 1)
                .param("size", 2)
                .when().get("http://localhost/8080/author")
                .then().extract().response();
        Assertions.assertEquals(200, response.statusCode());
    }
}
