package Day8;

import Day6_POJO.Spartan;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import javafx.scene.layout.Priority;
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
    int ids;
    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }


    @Test(priority = 1)
    public void POSTNewSpartan() {
        Spartan spo = new Spartan();
        spo.setName("Tromps");
        spo.setGender("Male");
        spo.setPhone(1223445677);
        Response response = given()
                .contentType(ContentType.JSON)
                .body(spo)
                .when()
                .post("/api/spartans");
        Assert.assertEquals(response.statusCode(), 201);
        ids = response.path("data.id");
    }
        @Test(priority = 2)
                public void putSP() {

            Map<String, Object> putSpartans = new HashMap<>();
            putSpartans.put("name", "smile");
            putSpartans.put("gender", "Male");
            putSpartans.put("phone", "1223445677");

            given().log().all()
                    .pathParam("id", ids)
                    .contentType(ContentType.JSON)
                    .and().body(putSpartans)
                    .when().put("/api/spartans/{id}")
                    .then().statusCode(204);
        }

          @Test(priority = 3)
          public void patchSP() {
            Map<String, Object> patch = new HashMap<>();
            patch.put("name", "smileagain");

            Response responce1 = given().contentType(ContentType.JSON)
                    .pathParam("id", ids)
                    .body(patch)
                    .when().patch("/api/spartans/{id}");
            Assert.assertEquals(responce1.statusCode(), 204);
        }
         @Test(priority = 4)
        public void getSP() {
    given().accept(ContentType.JSON)
            .pathParam("id", ids)
            .when().get("/api/spartans/{id}")
            .then().log().all().statusCode(200);
}
       @Test(priority = 5)
        public void deleteSP(){

        given().pathParam("id",ids)
                .when().delete("/api/spartans/{id}")
                .then().statusCode(204);


    }



}