package apitests;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;


public class CBTrainingWithJsonPath {

    @BeforeClass
    public void beforeclass() {
        baseURI = ConfigurationReader.get("cbt_api_url");
    }
    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 24112)
                .when().get("/student/{id}");

        //verify status code
        assertEquals(response.statusCode(),200);

        //assign response to jsonpath
        JsonPath jsonPath=response.jsonPath();

        //get values from jsonpath

        String firstName = jsonPath.getString("students.firstName[0]");
        System.out.println("firstName = " + firstName);

        String lastName = jsonPath.getString("students.lastName[0]");
        System.out.println("lastName = " + lastName);

        String phone = jsonPath.getString("students.contact[0].phone");
        System.out.println("phone = " + phone);

        //get values from jsonpath
        // GET CİTY AND ZİPCODE

        String city = jsonPath.getString("students.company[0].address.city");
        System.out.println("city = " + city);
        assertEquals(city,"Mayertville");


        int zipCode = jsonPath.getInt("students.company[0].address.zipCode");
        System.out.println("zipCode = " + zipCode);
        assertEquals(zipCode,23703);

        String firstName2= jsonPath.getString("students.firstName");
        System.out.println("firstName2 = " + firstName2);
        //farkı neden
        // response path hata veriyor , int int yapmak zarundasın yada stringi stringe atamak zorundasın
        //
        String firstname3 = response.path("students.firstName");
        System.out.println("firstname3 = " + firstname3);

        //burda hata verir cünkü zipcode int biz stringe atıyoruz. ama jsonpat olsaydı hata vermezdi
        String zipCode2= response.path("students.company[0].address.zipCode");
        System.out.println("zipCode2 = " + zipCode2);


    }

}