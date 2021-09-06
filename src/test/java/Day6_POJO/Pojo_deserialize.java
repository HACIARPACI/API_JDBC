package Day6_POJO;
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

public class Pojo_deserialize {

    @Test
    public  void  oneSpartanPojo(){

        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 15)
                .when().get("http://54.237.118.39:8000/api/spartans/{id}");
        assertEquals(response.statusCode(),200);
        //json to pojo--> de serialize to java custom
        //Json to Our spartan class object

        Spartan spartan15 = response.body().as(Spartan.class);

        System.out.println(spartan15);

        System.out.println("spartan15.getName() = " + spartan15.getName());
        System.out.println("spartan15.getPhone() = " + spartan15.getPhone());


        //
        assertEquals(spartan15.getName(),"Meta");
        assertEquals(spartan15.getId(),15);

    }
    @Test
    public void regionToPojo(){
        Response response = when().get("http://54.237.118.39:1000/ords/hr/regions");
        assertEquals(response.statusCode(),200);

        //JSON to POJO(region class)
        Regions regions = response.body().as(Regions.class);

        System.out.println("regions.getHasMore() = " + regions.getHasMore());
        System.out.println("regions.getCount() = " + regions.getCount());

        System.out.println(regions.getItems().get(0).getRegionName());
        System.out.println(regions.getItems().get(0).getRegionId());
        List<Item>items=regions.getItems();
        System.out.println(items.get(1).getRegionId());



    }
    @Test
    public  void gson_example(){
        // Gson is a library use for convert
        Gson gson = new Gson();

        //JSONto Java collection or POJO --> de serialization


        String myJsonData = "{\n" +
                "    \"id\": 15,\n" +
                "    \"name\": \"Meta\",\n" +
                "    \"gender\": \"Female\",\n" +
                "    \"phone\": 1938695106\n" +
                "}";
       Map<String,Object> map = gson.fromJson(myJsonData,Map.class);
        System.out.println(map);

        Spartan spartan15 = gson.fromJson(myJsonData,Spartan.class);
        System.out.println(spartan15);

        // **************** SERIALIZATION*************************
        // JAVA collection or Pojo to Json

        Spartan spartanEU= new Spartan(200,"Mike","Male",123344523);
        String jsonSpartanEU= gson.toJson(spartanEU);
        System.out.println(jsonSpartanEU);


    }
}
