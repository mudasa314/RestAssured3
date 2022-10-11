import POJO.Location;
import POJO.ToDo;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Tasks {
    /** Task 6
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * Converting Into POJO
     */
    @Test
    public void test1(){
        ToDo  todo=
                given()

                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos/2")
                        .then()
                        .statusCode(200)
                        .extract().as(ToDo.class)
                ;
        System.out.println("todo = " + todo);

    }
    @Test
    public void test2(){

        /**
         * Task 2
         * create a request to https://httpstat.us/203
         * expect status 203
         * expect content type TEXT
         */
                given()

                        .when()
                        .get("https://httpstat.us/203")
                        .then()
                        .log().body()
                        .statusCode(203)
                        .contentType(ContentType.TEXT)
                ;
    }
    @Test
    public void test3(){

        /**
         * Task 3
         * create a request to https://jsonplaceholder.typicode.com/todos/2
         * expect status 200
         * expect content type JSON
         * expect title in response body to be "quis ut nam facilis et officia qui"
         */
        given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title ", equalTo("quis ut nam facilis et officia qui"))
        ;
    }

}
