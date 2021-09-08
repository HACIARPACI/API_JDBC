package Day8;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
public class SpartanBasicAuth {

    @Test
    public  void test1(){

        given()
                .accept(ContentType.JSON)
                .and()
                .auth().basic("admin","admin")// hey im the man who is  on the password and username
        .when()                                     //yetkim yoksa 401 verir
                .get("http://54.237.118.39:8000/api/spartans")
        .then().log().all()
                .statusCode(200);
    }

}
