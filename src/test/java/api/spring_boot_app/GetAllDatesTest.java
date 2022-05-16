package api.spring_boot_app;

import api.BaseApiTest;
import api.endpoints.Endpoints;
import api.model.Date;
import api.model.Dates;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GetAllDatesTest extends BaseApiTest {

    private final Dates expectedDates;

    public GetAllDatesTest() {
        this.expectedDates = Dates.builder()
                .dates(
                        List.of(
                                Date.builder().date("10.12.2020").build(),
                                Date.builder().date("14.10.2021").build(),
                                Date.builder().date("10.05.2022").build()
                        )
                )
                .build();
    }

    @Test(testName = "Get list of all dates")
    public void getAllDatesTest() {

        Dates actualDates = given(reqSpec).when().
                    get(Endpoints.allDates).
                then().
                    statusCode(HttpStatus.SC_OK).
                extract().
                    body().as(Dates.class);

        Assert.assertTrue(this.expectedDates.equals(actualDates));
        //TODO: hamcrest assert
        //assertThat(actualDates.getDateList(), containsInAnyOrder(this.expectedDates.getDateList().toArray()));
    }

}
