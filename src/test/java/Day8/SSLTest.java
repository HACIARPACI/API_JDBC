package Day8;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class SSLTest {
    @Test
    public void test(){
        given()
                .relaxedHTTPSValidation()
                .when().get("https://untrusted-root.badssl.com/")
                .prettyPrint();

    }
    @Test
    public void keyStore(){
        given()
                .keyStore("pathoffile","password")
                .when().get("myapi");



    }



}
