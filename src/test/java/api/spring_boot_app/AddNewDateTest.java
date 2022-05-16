package api.spring_boot_app;

import api.BaseApiTest;
import api.endpoints.Endpoints;
import api.enums.ApiError;
import api.model.ApiException;
import api.model.Date;
import api.model.Dates;
import com.google.common.collect.Iterables;
import helpers.TestNGHelper;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AddNewDateTest extends BaseApiTest implements TestNGHelper {

    @DataProvider(name = "addNewDatePositiveTestDataProvider")
    public Object[][] addNewDatePositiveTestDataProvider() {
        return new Object[][]{
                {
                    "Add new date",
                    Date.builder().date("01.01.2020").build()
                }
        };
    }

    @Test(dataProvider = "addNewDatePositiveTestDataProvider")
    public void addNewDatePositiveTest(String name, Date expectedDate) {
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

        Assert.assertEquals(
                datesAfter.getDates().size(),
                datesBefore.getDates().size() + 1,
                "Number of dates in list doesn't match");
        Assert.assertTrue(actualDate.equals(expectedDate), "Expected date doesn't match actual date");
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
                        "Add date with null data",
                        Date.builder().date(null).build(),
                        ApiError.INPUT_DATE_IS_NULL
                },
                {
                        "Add new date with duplicate keys in request body",
                        "{\"date\":\"12.12.2012\",\"date\":\"12.11.2012\"}",
                        ApiError.INPUT_DATE_IS_NULL //TODO: Добавить исклбчение
                },
                {
                        "Add new date with year value=MAX+1",
                        Date.builder().date("31.12.+1000000000").build(),
                        ApiError.INPUT_DATE_IS_NULL //TODO: Добавить исклбчение
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
        Date actualDate = Iterables.getLast(datesAfter.getDates());

        Assert.assertEquals(
                datesAfter.getDates().size(),
                datesBefore.getDates().size(),
                "Number of dates in list doesn't match");
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
