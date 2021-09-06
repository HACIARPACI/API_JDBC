package apitests;

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

public class jsonToJavaCollection {

    @BeforeClass
    public void beforeclass() {
        baseURI = ConfigurationReader.get("spartan_api_url");
    }
    @Test
    public  void SpartanToMap(){

        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 15)
                .when().get("/api/spartans/{id}");
        assertEquals(response.statusCode(),200);

        //we will  convert json to java map
        Map<String,Object> jsonDataMap = response.body().as(Map.class);
        System.out.println("jsonDataMap = " + jsonDataMap);

        String name = (String) jsonDataMap.get("name");
        assertEquals(name,"Meta");

        BigDecimal phone = new BigDecimal(String.valueOf(jsonDataMap.get("phone")));
        System.out.println("phone = " + phone);


    }
    @Test
    public void allSpartansToListOfMap(){

        Response response = given().accept(ContentType.JSON)
                .when().get("/api/spartans");
        assertEquals(response.statusCode(),200);

        //we need to de serialize JSON response tp list of MAps

      List<Map<String,Object>>allSpartanList  = response.body().as(List.class);
        System.out.println(allSpartanList);

        //print second spartan first name
        System.out.println("allSpartanList.get(1).get(\"name\") = " + allSpartanList.get(1).get("name"));

        //store spartan3 in a Map
        Map<String ,Object>spartan3 = allSpartanList.get(2);
        System.out.println("spartan3 = " + spartan3);
    }
    @Test
    public void regionToMap(){
        Response response = when().get("http://54.237.118.39:1000/ords/hr/regions");

        assertEquals(response.statusCode(),200);
        //we de-serialize JSON response to Map
        Map<String,Object>regionMap =response.body().as(Map.class);

        System.out.println("regionMap.get(\"count\") = " + regionMap.get("count"));
        System.out.println("regionMap.get(\"limit\") = " + regionMap.get("limit"));
        System.out.println("regionMap.get(\"hasMore\") = " + regionMap.get("hasMore"));

        List<Map<String,Object>> itemsList = (List<Map<String, Object>>) regionMap.get("items");

        System.out.println(itemsList.get(0).get("region_name"));

        System.out.println(itemsList.get(0).get("links"));





    }
    }