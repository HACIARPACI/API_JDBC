package apitests;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.TestInstance;
import utilities.ConfigurationReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import  static org.hamcrest.Matchers.*;

public class homework {
   /* Q1:
            - Given accept type is Json
- Path param value- US
- When users sends request to /countries
- Then status code is 200
            - And Content - Type is Json
- And country_id is US
- And Country_name is United States of America
- And Region_id is*/
   @BeforeClass
   public void beforeclass() {
       baseURI = ConfigurationReader.get("hr_api_url");
   }
    @Test
    public void test1(){
        given().accept(ContentType.JSON)
                .and().pathParam("id","US")
                .when().get("/countries/{id}")
                .then().assertThat().statusCode(200)
                .and().contentType("application/json")
                .body("country_id",equalTo("US"),"country_name",equalTo("United States of America"),
                        "region_id",equalTo(2));
    }
    /*
    - Given accept type is Json
- Query param value - q={"department_id":80}
- When users sends request to /employees
- Then status code is 200
- And Content - Type is Json
- And all job_ids start with 'SA'
- And all department_ids are 80
- Count is 25
    */

    @Test
    public  void  test2(){
       Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"department_id\":80}")
                .when().get("/employees");

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");

        JsonPath jsonPath= response.jsonPath();
        List<String> list = jsonPath.getList("items.job_id");
        for (String s : list) {
            Assert.assertTrue(s.startsWith("SA"));
        }

        List<Integer> dep_ids = jsonPath.getList("items.department_id");
        for (int each : dep_ids) {
            Assert.assertEquals(each,80);
        }
    }
/*- Given accept type is Json
-Query param value q= region_id 3
            - When users sends request to /countries
- Then status code is 200
            - And all regions_id is 3
            - And count is 6
            - And hasMore is false
            - And Country_name are;
    Australia,China,India,Japan,Malaysia,Singapore*/

    @Test
    public  void test3(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_id\":3}")
                .when().get("/countries");
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");

        JsonPath jsonPath= response.jsonPath();
        List<Integer> regionIds = jsonPath.getList("items.region_id");
        for (int regionId : regionIds) {
            Assert.assertEquals(regionId,3);
        }
        int count = jsonPath.getInt("count");
        Assert.assertEquals(count,6);

        boolean hasMore = jsonPath.getBoolean("hasMore");
        Assert.assertEquals(hasMore,false);

        List<String> countries = jsonPath.getList("items.country_name");
      List<String>newCountries= Arrays.asList("Australia", "China", "India", "Japan", "Malaysia", "Singapore");
    Assert.assertEquals(countries,newCountries);
         }


    }














