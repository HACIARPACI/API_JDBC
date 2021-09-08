package Day8;

import Day6_POJO.Spartan;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class SpartanFlow {
    int id;
    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }


    @Test(priority = 1)
    public void POSTNewSpartan(){
        Spartan spo = new Spartan();
        spo.setName("Tromps");
        spo.setGender("Male");
        spo.setPhone(1223445677);
        Response response = given()
                .contentType(ContentType.JSON)
                .body(spo)
                .when()
                .post("/api/spartans");
        Assert.assertEquals(response.statusCode(),201);
        int ids=response.path("data.id");


        Map<String,Object>put= new HashMap<>();
        put.put("name","smile");
        put.put("gender","Male");
        put.put("phone","1223445677");

        given().log().all()
                .pathParam("id",ids)
                .contentType(ContentType.JSON)
                .and().body(put)
                .when().put("/api/spartans/{id}")
                .then().statusCode(204);


        Map<String,Object>patch= new HashMap<>();
        patch.put("name","smileagain");

        Response responce1 = given().contentType(ContentType.JSON)
                .pathParam("id", ids)
                .body(patch)
                .when().patch("/api/spartans/{id}");
        Assert.assertEquals(responce1.statusCode(),204);


        given().accept(ContentType.JSON)
                .pathParam("id",ids)
                .when().get("/api/spartans/{id}")
                .then().log().all().statusCode(200);


        given().pathParam("id",ids)
                .when().delete("/api/spartans/{id}")
                .then().statusCode(204);


    }



}