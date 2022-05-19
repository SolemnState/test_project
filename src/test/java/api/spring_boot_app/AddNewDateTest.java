package api.spring_boot_app;

import api.BaseApiTest;
import com.innotech.data.DateDAO;
import com.innotech.endpoints.Endpoints;
import com.innotech.enums.ApiError;
import com.innotech.model.ApiException;
import com.innotech.model.Date;
import com.innotech.model.Dates;
import com.google.common.collect.Iterables;
import helpers.TestNGHelper;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static helpers.DateHelper.generateDate;
import static io.restassured.RestAssured.given;

public class AddNewDateTest extends BaseApiTest implements TestNGHelper {

    @Autowired
    DateDAO dateDAO;

    @DataProvider(name = "addNewDatePositiveTestDataProvider")
    public Object[][] addNewDatePositiveTestDataProvider() {
        return new Object[][]{
                {
                    "Add new date",
                    Date.builder().date(generateDate()).build()
                }
        };
    }

    @Test(dataProvider = "addNewDatePositiveTestDataProvider")
    public void addNewDatePositiveTest(String name, Date expectedDate) throws SQLException {
        Dates datesBefore = given(reqSpec).when()
                .get(Endpoints.allDates)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body().as(Dates.class);

        given(reqSpec)
                .body(expectedDate)
                .when()
                .post(Endpoints.addDate)
                .then()
                .statusCode(HttpStatus.SC_OK);

        Dates datesAfter = given(reqSpec).when()
                .get(Endpoints.allDates)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body().as(Dates.class);
        Date actualDate = Iterables.getLast(datesAfter.getDates());

        Date dbDate = dateDAO.getDateByValue(expectedDate.getDate());

        Assert.assertEquals(
                datesAfter.getDates().size(),
                datesBefore.getDates().size() + 1,
                "Number of dates in list doesn't match");
        Assert.assertTrue(actualDate.equals(expectedDate), "Expected date doesn't match actual date");
        Assert.assertTrue(dbDate.equals(expectedDate), "Expected date in DB doesn't match actual date");
    }

    @DataProvider(name = "addNewDateNegativeTestDataProvider")
    public Object[][] addNewDateNegativeTestDataProvider() {
        return new Object[][]{
                {
                        "Add date with wrong number of days",
                        Date.builder().date("32.01.2020").build(),
                        ApiError.WRONG_DATE_FORMAT
                },
                {
                        "Add date with wrong format",
                        Date.builder().date("01/01/2020").build(),
                        ApiError.WRONG_DATE_FORMAT
                },
                {
                        "Add date with invalid data",
                        Date.builder().date("sadasqwe12312edqwasd").build(),
                        ApiError.WRONG_DATE_FORMAT
                },
                {
                        "Add date with null date",
                        Date.builder().date(null).build(),
                        ApiError.INPUT_DATE_IS_NULL
                },
                {
                        "Add date with empty date",
                        "{\"date\":\"\"}",
                        ApiError.INPUT_DATE_IS_NULL
                },
                {
                        "Add new date with duplicate keys in request body",
                        "{\"date\":\"12.12.2012\",\"date\":\"12.11.2012\"}",
                        ApiError.INPUT_JSON_IS_NOT_VALID
                },
                {
                        "Add new date with year value=MAX+1",
                        Date.builder().date("31.12.+1000000000").build(),
                        ApiError.WRONG_DATE_FORMAT
                }
        };
    }

    @Test(dataProvider = "addNewDateNegativeTestDataProvider")
    public void addNewDateNegativeTest(String name, Object errorDate, ApiError expectedError) {
        Dates datesBefore = given(reqSpec).when()
                .get(Endpoints.allDates)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body().as(Dates.class);

        ApiException exception = given(reqSpec)
                .body(errorDate)
                .when()
                .post(Endpoints.addDate)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .body().as(ApiException.class);

        Dates datesAfter = given(reqSpec).when()
                .get(Endpoints.allDates)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body().as(Dates.class);

        Assert.assertEquals(
                datesAfter.getDates().size(),
                datesBefore.getDates().size(),
                "Количество записей в ответах до и после попытки добавления не совпадает с ожидаемым");
        Assert.assertEquals(
                exception.getErrorCode(),
                expectedError.getCode(),
                "Код ошибки не совпадает с ожидаемым");
        Assert.assertEquals(
                exception.getMessage(),
                expectedError.getMessage(),
                "Сообщение об ошибке не совпадает с ожидаемым");
    }

}
