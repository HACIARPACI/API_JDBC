package apitests;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.TestInstance;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import  static org.hamcrest.Matchers.*;
public class deneme {
    /*
     given accept type is Json
     And path param id is 20;
     When user sends a get request to spartans/{id}
     Then status code is 200
     And content type is Json
     And json data has following
         "id": 15,
         "name": "Meta",
         "gender": "Female",
         "phone": 1938695106
      */
    @Test
    public void test1() {
        given().accept(ContentType.JSON)
                .and().pathParam("id", 20)
                .when().get("http://54.237.118.39:8000/api/spartans/{id}")
                .then().statusCode(200).and().assertThat().contentType("application/json")
                .and().assertThat().body("id", equalTo(20), "name", equalTo("Lothario")
                , "phone", equalTo(7551551687l));
    }

    @Test
    public void test2() {
        given().accept(ContentType.JSON)
                .and().pathParam("id", 23965)
                .when().get("http://api.cybertektraining.com/student/{id}")
                .then().statusCode(200)
                .and().assertThat().contentType("application/json;charset=UTF-8")
                .assertThat().body("students.firstName[0]", equalTo("Ihsan")
                , "students.batch[0]", equalTo(22));

    }

    //then yazmazsan çıkmıyor
    @Test
    public void test3() {
        given().accept(ContentType.JSON)
                .and().pathParam("name", "Construction")
                .when().get("http://api.cybertektraining.com/teacher/department/{name}")
                .then().statusCode(200).and().contentType("application/json;charset=UTF-8")
                .and().header("Connection", equalTo("Keep-Alive"))
                .assertThat().body("teachers.firstName", hasItems("Sol"))
                .log().all();

    }

    @Test
    public void test4() {
        Response response = given().accept(ContentType.JSON)
                .and().get("http://54.237.118.39:1000/ords/hr/countries");
        Assert.assertEquals(response.statusCode(), 200);

        JsonPath jsonPath = response.jsonPath();

        List<Object> list = jsonPath.getList("items.findAll{it.region_id==1}.country_name");
        System.out.println("list = " + list);


    }

    @Test
    public void test5() {
        Response response = given().accept(ContentType.JSON)
                .and().get("http://54.237.118.39:1000/ords/hr/countries");
        Assert.assertEquals(response.statusCode(), 200);

        JsonPath jsonPath = response.jsonPath();

        List<String> list = jsonPath.getList("items.findAll{it.country_id==\"AR\"}.country_name");
        System.out.println("country = " + list);

    //         String country= jsonPath.getString("items.findAll{it.country_id==\"AR\"}.country_name);
        String country= jsonPath.getString("items.country_id[0]");
        System.out.println("country = " + country);

    }
    @Test
    public void test6(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", "Computer")
                .when().get("http://api.cybertektraining.com/teacher/department/{id}");

            Assert.assertEquals(response.statusCode(),200);

            JsonPath jsonPath= response.jsonPath();
        List<String> list = jsonPath.getList("teachers.department");
        for (String s : list) {
            Assert.assertEquals(s,"Computer");
        }


    }

    @Test
    public void test7(){

        given().accept(ContentType.JSON)
                .and().pathParam("id",24112)
                .when().get("http://api.cybertektraining.com/student/{id}")
                .then().assertThat().statusCode(200)
                .and().contentType("application/json;charset=UTF-8")
                .assertThat().header("Content-Length","258")
                .assertThat().headers("Connection",equalTo("Keep-Alive"))
                .assertThat().body("students.batch[0]",equalTo(17))
                .body("students.firstName",hasItems("Mira"))
                ;

    }

    @Test
    public void test9(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 15)
                .when().get("http://54.237.118.39:8000/api/spartans/{id}");

       Map<String,Object>body = response.body().as(Map.class);

        String name =(String) body.get("name");
        Object id = body.get("id");
        //        long phone=(long)body.get("phone");
      //  System.out.println("phone = " + phone);
        System.out.println("name = " + name);
        System.out.println("id = " + id);

    }
    @Test
    public void test10(){

                given()
                        .baseUri("http://54.237.118.39:8000")
                        .basePath("/api")
                .when()
                        .get("/hello")
                .then()
                        .statusCode(200)
                        .body(is("Hello from Sparta"))
                        .header("Connection",is("keep-alive"))
                        .contentType(ContentType.TEXT)
                        .body(notNullValue());



    }

}









