package apitests;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.*;
import utilities.ConfigurationReader;
import java.util.List;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class Zipcode2 {


    Response response;

    @Test
    public void test1() {
       response =  given().accept(ContentType.JSON)
               .and().pathParam("code",22031)
               .when().get("https://api.zippopotam.us/us/{code}");
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        assertEquals(response.header("server"), "cloudflare");
        assertTrue(response.header("Report-To").contains("endpoints"));

        String post_code = response.path("'post code'");
        String country = response.path("country");
        String countryAbb = response.path("'country abbreviation'");
        String placeName = response.path(("places.'place name'[0]"));
        String placeState = response.path("places.state[0]");
        String placeLatitude = response.path("places.latitude[0]");

        assertEquals(post_code,"22031");
        assertEquals(country,"United States");
        assertEquals(countryAbb,"US");
        assertEquals(placeName,"Fairfax" );
        assertEquals(placeState,"Virginia");
        assertEquals(placeLatitude,"38.8604");


        //practice with Gpath Json
        JsonPath jsonPath = response.jsonPath();

        String post_codeJson = response.path("'post code'");
        String countryJson = jsonPath.getString("country");
        String countryAbbJson = jsonPath.getString("'country abbreviation'");
        String placeNameJson = jsonPath.getString(("places.'place name'[0]"));
        String placeStateJson = jsonPath.getString("places.state[0]");
        String placeLatitudeJson = jsonPath.getString("places.latitude[0]");

        assertEquals(post_codeJson,"22031");
        assertEquals(countryJson,"United States");
        assertEquals(countryAbbJson,"US");
        assertEquals(placeNameJson,"Fairfax" );
        assertEquals(placeStateJson,"Virginia");
        assertEquals(placeLatitudeJson,"38.8604");

    }
    @Test
    public void test2(){
        response = given().accept(ContentType.JSON)
                .and().pathParam("zipcode", 50000)
                .when().get("/us/{zipcode}");
        assertEquals(response.statusCode(),404);
        assertEquals(response.contentType(),"application/json");
    }

    @Test
    public void test3(){
        response = given().accept(ContentType.JSON)
                .and().pathParam("state","va")
                .and().pathParam("city", "fairfax")
                .when().get("/us/{state}/{city}");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        String countryAbbr = response.path("'country abbreviation'");
        String country = response.path("country");

        List<String> placeName = response.path("places.'place name'");
        //  System.out.println(placeName);
        for (String eachPlaceName : placeName) {
            assertTrue(eachPlaceName.contains("Fairfax"));
        }
        List<String> postCode = response.path("places.'post code'");
        //    System.out.println(postCode);
        for (String eachPostCode : postCode) {
            assertTrue(eachPostCode.substring(0,2).contains("22"));
        }

    }

}
