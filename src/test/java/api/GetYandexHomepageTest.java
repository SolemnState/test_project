package api;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetYandexHomepageTest {

    @BeforeClass
    public void beforeClass() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    public void getHomePageTest() {
        given().
                relaxedHTTPSValidation().
        when().
                get("https://ya.ru").
        then().
                statusCode(HttpStatus.SC_OK);
    }
}
