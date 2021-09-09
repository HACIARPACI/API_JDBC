package HW;
import Day6_POJO.Spartan;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import utilities.ExcelUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
public class HW2{
//Homework-2
//-Create one mackaroo api for name,gender,phone
//send get request to retrieve random info from that api
//use those info to send post request to spartan

  /*  @BeforeClass
    public void before(){
        baseURI= ConfigurationReader.get("makUrl");
    }*/
@Test
    public void test()  {
    Response response = given().accept(ContentType.JSON)
            .queryParam("key", "bbf10d10")
            .when()
            .get(ConfigurationReader.get("makUrl"));
    List<Map<String, Object>> allMockList = response.body().as(List.class);

    for (Map<String, Object> mockMap : allMockList) {

        Response postResponse = given().log().all()
                .accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(mockMap)
                .when()
                .post(ConfigurationReader.get("spartan_api_url") + "/api/spartans");

        // verify status code
        Assert.assertEquals(postResponse.statusCode(),201);

        //get request
        int idAfterPost = postResponse.path("data.id");

        //after post request, send a get request to generated spartan
        given().accept(ContentType.JSON)
                .pathParam("id",idAfterPost)
                .when().get(ConfigurationReader.get("spartan_api_url")+"/api/spartans/{id}")
                .then().statusCode(200).log().all();
    }
}
  //  response.prettyPrint();
//    List<Map<String,Object>>spartans=response.body().as(List.class);
//
//
//    List<Integer>idsOfSpartanAdded=new ArrayList<>();
//
//    for (Map<String, Object> spartan : spartans) {
//        Response response2;
//        response2 = given().accept(ContentType.JSON)
//                .body(spartan)
//                .when()

//                .post(ConfigurationReader.get("spartan_api_url") + "/api/spartans");
//
//       // response2.prettyPrint();
//
//        Assert.assertEquals(response2.statusCode(),201);
//
//        idsOfSpartanAdded.add( response2.path("data.id"));
//
//    }
//
//
//    System.out.println("idsOfSpartanAdded = " + idsOfSpartanAdded);
//    Response response1 = given().accept(ContentType.JSON)
//            .when()
//            .get(ConfigurationReader.get("spartan_api_url" )+ "/api/spartans");
//    List<Integer>allSpartansIds=response1.path("id");
//
//    response1.prettyPrint();
//
//    Assert.assertTrue(allSpartansIds.containsAll(idsOfSpartanAdded));


}


