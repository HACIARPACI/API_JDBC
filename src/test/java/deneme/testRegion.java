package deneme;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
public class testRegion {
   @Test
   public  void reggoTest(){
       Response response = given()
               .accept(ContentType.JSON)
               .and().queryParam("q", "{\"region_id\":1}")
               .when().get("http://54.237.118.39:1000/ords/hr/countries");
       assertEquals(response.statusCode(),200);

       Region1 region1= response.body().as(Region1.class);
       System.out.println(region1.getCount());
       System.out.println(region1.getItems().get(3).getCountryName());

      List<Item>regionFirst= region1.getItems();
       System.out.println(regionFirst.get(0).getCountryId());




   }


}
