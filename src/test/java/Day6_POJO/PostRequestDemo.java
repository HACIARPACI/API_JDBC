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
    public void beforeclass() {
        baseURI = ConfigurationReader.get("spartan_api_url");
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
//@Test
//public  void  test1(){
//    Spartan alfa = new Spartan();
//    alfa.setGender("Male");
//    alfa.setPhone(8877445596l);
//    alfa.setName("MikeEU");
//
//    Response response = given().accept(ContentType.JSON)
//            .and().contentType(ContentType.JSON)
//            .body(alfa)
//            .post("/api/spartans");
//
//     int value = response.path("data.id");
//
//     given().accept(ContentType.JSON)
//             .pathParam("id",value)
//             .when()
//                .get("api/spartans/{id}")
//             .then()
//                .statusCode(200).log().all();
//
//}

   @Test
    public void PostNewSpartan() {

        String jsonBody = "{\n" +
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

        assertEquals(response.statusCode(), 201);
        assertEquals(response.contentType(), "application/json");

        //verify successful message
        String actualMessage = response.path("success");
        String expectedMessage = "A Spartan is Born!";
        assertEquals(expectedMessage, actualMessage);
        //shortWay
        // assertEquals(response.path("success"),"A Spartan is Born!");

        //assertion for spartan data
        String name = response.path("data.name");
        String gender = response.path("data.gender");
        long phone = response.path("data.phone");

        assertEquals(name, "MikeEU");
        assertEquals(gender, "Male");
        assertEquals(phone, 8877445596l);

    }

    @Test
    public void PostNewSpartan2() {
        //create a map to keep request json body information
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("name", "MikeEU2");
        requestMap.put("gender", "Male");
        requestMap.put("phone", 8877445596l);

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
                .and().body("success", is("A Spartan is Born!"),
                "data.name", is("MikeEU2"),
                "data.gender", is("Male"),
                "data.phone", is(8877445596l));

    }
    //Optional homeworks
    //Homework-1
    //1-Create csv file from mackaroo website, which includes name,gender,phone
    //2-Download excel file
    //3- using testng data provider and apache poi create data driven posting from spartan


    //Homework-2
    //-Create one mackaroo api for name,gender,phone
    //send get request to retrieve random info from that api
    //use those info to send post request to spartan


    @Test
    public void PostNewSpartan3() {

        Spartan spartanEU = new Spartan();
        spartanEU.setName("MikeEU3");
        spartanEU.setGender("Male");
        spartanEU.setPhone(1239873457);


        given().log().all()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                // burda requestMap Json değil mao ama hata vermiyor
                //çünkü doby metaodunun içinde otomatik(convert) serialize oluyor
                .body(spartanEU)
                .when()
                .post("/api/spartans")
                .then().log().all()
                .assertThat().statusCode(201)
                .and().contentType("application/json")
                .and().body("success", is("A Spartan is Born!"),
                "data.name", is("MikeEU3"),
                "data.gender", is("Male"),
                "data.phone", is(1239873457));


    }

    @Test
    public void PostNewSpartan4() {
        Spartan spartanEU = new Spartan();
        spartanEU.setName("MikeEU3");
        spartanEU.setGender("Male");
        spartanEU.setPhone(8877445596l);


        Response response = given().log().all()
                .accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(spartanEU)
                .when()
                .post("/api/spartans");

        int idFromPost = response.path("data.id");
        System.out.println("id = " + idFromPost);
        //after post request, send a get request to generated spartan
                //get request
        given().accept(ContentType.JSON)
                .pathParam("id",idFromPost)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200).log().all();




    }
}