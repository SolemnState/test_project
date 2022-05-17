package api.spring_boot_app;

import api.BaseApiTest;
import com.innotech.endpoints.Endpoints;
import com.innotech.model.Date;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Instant;
import java.time.ZoneId;

import static io.restassured.RestAssured.*;

public class GetCurrentDateTest extends BaseApiTest {

    @Test(testName = "Get current date")
    public void getCurrentDateTest() {

        String currentDate = Instant.now().atZone(ZoneId.of("UTC")).toLocalDate().format(apiDateFormat);

        Date expectedDate = Date.builder()
                .date(currentDate)
                .build();

        Date actualDate = given(reqSpec).when().
                get(Endpoints.currentDate).
            then().
                statusCode(HttpStatus.SC_OK).
            extract().
                body().as(Date.class);

        Assert.assertTrue(expectedDate.equals(actualDate));
    }
}
