package api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;

import java.time.format.DateTimeFormatter;

@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class BaseApiTest extends AbstractTestNGSpringContextTests {

    protected DateTimeFormatter apiDateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    protected RequestSpecification reqSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8081)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .build();

    protected RequestSpecification mockReqSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8082)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .build();

    @BeforeClass
    public void beforeClass() {
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
}
