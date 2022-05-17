package api.spring_boot_app;

import api.BaseApiTest;
import com.innotech.endpoints.Endpoints;
import com.innotech.model.Date;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;

public class MockServiceTest extends BaseApiTest {

    @Test(testName = "Get date from mock service")
    public void getCurrentDateTest() {

        Date expectedDate = given(mockReqSpec).when().
                get(Endpoints.mock).
                then().
                statusCode(HttpStatus.SC_OK).
                extract().
                body().as(Date.class);

        Date actualDate = given(reqSpec).when().
                get(Endpoints.mockService).
                then().
                statusCode(HttpStatus.SC_OK).
                extract().
                body().as(Date.class);

        Assert.assertTrue(expectedDate.equals(actualDate));
    }
}
