package apitests;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.TestInstance;

import static io.restassured.RestAssured.*;
import  static org.hamcrest.Matchers.*;
/*
Given Accept application/json
And path zipcode is 22031
When I send a GET request to /us endpoint
Then status code must be 200
And content type must be application/json
And Server header is cloudflare
And Report-To header exists
And body should contains following information
    post code is 22031
    country  is United States
    country abbreviation is US
    place name is Fairfax
    state is Virginia
    latitude is 38.8604
*/

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class zipCode {
    @Test
    public void zipo1(){
        given().accept(ContentType.JSON)
                .and().pathParam("code",22031)
                .when().get("https://api.zippopotam.us/us/{code}")
                .then().statusCode(200).contentType("application/json")
                .and().header("Server",equalTo("cloudflare"))
                .and().header("Report-To",notNullValue())
                .and().assertThat().body("'post code'",equalTo("22031")
                ,"places.state[0]",equalTo("Virginia"),
              "'country abbreviation'",equalTo("US")
             ,"places.latitude[0]",equalTo("38.8604"),"country",equalTo("United States")
        );
    }
    /*
    Given Accept application/json
And path zipcode is 50000
When I send a GET request to /us endpoint
Then status code must be 404
And content type must be application/json
     */
@Test
    public void zipo2(){
    given().accept(ContentType.JSON)
            .and().pathParam("code",50000)
            .when().get("https://api.zippopotam.us/us/{code}")
            .then().statusCode(404).contentType("application/json")   ;


}
/*
Given Accept application/json
And path state is va
And path city is farifax
When I send a GET request to /us endpoint
Then status code must be 200
And content type must be application/json
And payload should contains following information
    country abbreviation is US
    country  United States
    place name  Fairfax
    each places must contains fairfax as a value
    each post code must start with 22
 */
@Test
    public void zipo3(){
   /* Map<String,Object>params= new HashMap<>();
    params.put("state","va");
    params.put("city","farifax");*/
    Response response = given().accept(ContentType.JSON)
            .and().pathParam("state","va")
            .and().pathParam("city","fairfax")
            .when().get("https://api.zippopotam.us/us/{state}/{city}");
    Assert.assertEquals(response.statusCode(),200);
    Assert.assertEquals(response.contentType(),"application/json");

    JsonPath jsonPath= response.jsonPath();
    String country = jsonPath.getString("country");
    Assert.assertEquals(country,"United States");

    String countAbb = jsonPath.getString("'country abbreviation'");
    Assert.assertEquals(countAbb,"US");


    List<String> placeNames = jsonPath.getList("places.'place name'");
    for (String s : placeNames) {
        Assert.assertTrue(s.contains("Fairfax"));
    }

    List<String> postCodes = jsonPath.getList("places.'post code'");
    for (String postCode : postCodes) {
        Assert.assertTrue(postCode.startsWith("22"));
    }

    String post0 = jsonPath.getString("places.'post code'");
    System.out.println("post0 = " + post0);


}

}
