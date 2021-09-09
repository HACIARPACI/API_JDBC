package Day8;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class JsonSchemaValidationDemo {
    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }
    @Test
    public  void  JsonSchemaValidationForSpartans(){

        given().accept(ContentType.JSON)
                .pathParam("id",17)
        .when()
                .get("/api/spartans/{id}")
        .then().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SingleSpartanSchema.json"));
                //alınan json json schema structura uygun mu onu test ediyoruz


    }


}
