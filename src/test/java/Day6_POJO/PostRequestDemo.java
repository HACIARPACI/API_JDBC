package Day6_POJO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import oracle.jdbc.proxy.annotation.Post;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;
public class PostRequestDemo {

    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }

        /*
    Given accept type and Content type is JSON
    And request json body is:
    {
      "gender":"Male",
      "name":"MikeEU",
      "phone":8877445596
   }
    When user sends POST request to '/api/spartans'
    Then status code 201
    And content type should be application/json
    And json payload/response/body should contain:
    "A Spartan is Born!" message
    and same data what is posted
 */


    @Test
    public  void PostNewSpartan(){

        String jsonBody ="{\n" +
                "      \"gender\":\"Male\",\n" +
                "      \"name\":\"MikeEU\",\n" +
                "      \"phone\":8877445596\n" +
                "   }";

        Response response = given().log().all()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .and().body(jsonBody)
                .when()
                .post("/api/spartans");

        assertEquals(response.statusCode(),201);
        assertEquals(response.contentType(),"application/json");

        //verify successful message
        String actualMessage = response.path("success");
        String expectedMessage= "A Spartan is Born!";
        assertEquals(expectedMessage,actualMessage);
        //shortWay
        // assertEquals(response.path("success"),"A Spartan is Born!");

        //assertion for spartan data
        String name=response.path("data.name");
        String gender= response.path("data.gender");
        long phone= response.path("data.phone");

        assertEquals(name,"MikeEU");
        assertEquals(gender,"Male");
        assertEquals(phone,8877445596l);

    }

    @Test
    public void PostNewSpartan2(){
        //create a map to keep request json body information
        Map<String ,Object>requestMap= new HashMap<>();
        requestMap.put("name","MikeEU2");
        requestMap.put("gender","Male");
        requestMap.put("phone",8877445596l);

        given().log().all()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                // burda requestMap Json değil mao ama hata vermiyor
                //çünkü doby metaodunun içinde otomatik(convert) serialize oluyor
                .body(requestMap)
                .when()
                .post("/api/spartans")
        .then().log().all()
                .assertThat().statusCode(201)
               .and().contentType("application/json")
               .and().body("success",is("A Spartan is Born!"),
                "data.name",is("MikeEU2"),
                "data.gender",is("Male"),
                "data.phone",is(8877445596l));

    }



}
