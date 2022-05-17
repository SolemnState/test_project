package api.spring_boot_app;

import api.BaseApiTest;
import com.innotech.endpoints.Endpoints;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import mock.date.DateResponseTransformer;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.entity.ContentType.TEXT_PLAIN;

public class MockTest extends BaseApiTest {

    private WireMockServer mockServer;

    @BeforeClass
    public void configure() {
        final int port = 8090;
        mockServer = new WireMockServer(wireMockConfig()
                .port(port)
                .extensions(DateResponseTransformer.class));
        mockServer.start();
        WireMock.configureFor("localhost", port);
        this.setUpStub();
    }

    @Test
    public void mockTest() {
        given().when().get("http://localhost:8090/mock").then().statusCode(SC_OK);
    }

    @AfterClass(alwaysRun = true)
    public void stopMockServer(){
        mockServer.stop();
    }

    private void setUpStub() {
        stubFor(get(urlPathEqualTo(Endpoints.mock))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, TEXT_PLAIN.toString())
                        .withTransformers("date-stub-transformer")
                        .withStatus(SC_OK)
                )
        );
    }
}
