package HW;
import Day6_POJO.Spartan;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import utilities.ExcelUtil;

import java.math.BigDecimal;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;



public class Hw1 {
    //Homework-1
    //1-Create csv file from mackaroo website, which includes name,gender,phone
    //2-Download excel file
    //3- using testng data provider and apache poi create data driven posting from spartan
    @BeforeClass
    public void before(){
    baseURI=ConfigurationReader.get("spartan_api_url");
    }

    @DataProvider
    public Object[][] addSpartan(){

        ExcelUtil spartans= new ExcelUtil("src/test/resources/MOCK_DATA.xlsx","data");
        String[][]dataArray=spartans.getDataArrayWithoutFirstRow();
        return dataArray;
    }

    @Test(dataProvider = "addSpartan")
    public void testSpartan(String name ,String gender, String phone ){

        BigDecimal bd = new BigDecimal(phone);

        Spartan newSpas= new Spartan();
        newSpas.setName(name);
        newSpas.setGender(gender);
        newSpas.setPhone(bd.longValue());

        Response response = given()
                .accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(newSpas)
                .when()
                .post("/api/spartans");
        Assert.assertEquals(response.statusCode(),201);

       int  ids = response.path("data.id");
        given().accept(ContentType.JSON)
                .pathParam("id",ids)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200).log().all();




    }


}
