package api.spring_boot_app;

import api.BaseApiTest;
import com.innotech.data.DateDAO;
import com.innotech.endpoints.Endpoints;
import com.innotech.model.Date;
import com.innotech.model.Dates;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static io.restassured.RestAssured.given;

public class GetAllDatesTest extends BaseApiTest {

    @Autowired
    DateDAO dateDAO;

    @Test(testName = "Get list of all dates")
    public void getAllDatesTest() throws SQLException {
        Dates expectedDates = dateDAO.getAllDates();
        Dates actualDates = given(reqSpec).when().
                    get(Endpoints.allDates).
                then().
                    statusCode(HttpStatus.SC_OK).
                extract().
                    body().as(Dates.class);

        Assert.assertTrue(expectedDates.equals(actualDates));
        //TODO: hamcrest assert
        //assertThat(actualDates.getDateList(), containsInAnyOrder(this.expectedDates.getDateList().toArray()));
    }
}
