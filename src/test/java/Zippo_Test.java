import POJO.Location;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Zippo_Test {

    @Test
    public void test(){
        given()
                // hazırlıkişlermleri yapılacak (token, send body, paremetreler)
                .when()
                // link ve metod veriyoruz
                .then()
        // asetion ve verileri ele alma
        ; }
    @Test
    public void statusCodeTest(){
        given()
                // hazırlıkişlermleri yapılacak (token, send body, paremetreler)

                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body() // sonuçları yazdırmayı yapıyor yani responsu
                .statusCode(200)
        ;
    }
    @Test
    public void contentTypeTest(){
        given()
                // hazırlıkişlermleri yapılacak (token, send body, paremetreler)

                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body() // sonuçları yazdırmayı yapıyor yani responsu
                .statusCode(200)
                .contentType(ContentType.JSON)
        ;
    }
    @Test
    public void checkStateInResponceBody(){
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body() // sonuçları yazdırmayı yapıyor yani responsu
                .body("country", equalTo("United States")) // body.country== unites states?
                .statusCode(200)
        ;
    }
    @Test
    public void bodyJsonPathTest(){
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body() // sonuçları yazdırmayı yapıyor yani responsu
                .body("places[0].state", equalTo("California")) // body.country== unites states?
                .statusCode(200)
        ;
    }
    @Test
    public void bodyJsonPathTest2(){
        given()

                .when()
                .get("http://api.zippopotam.us/tr/01000")
                .then()
                .log().body() // sonuçları yazdırmayı yapıyor yani responsu
                .body("places.'place name' ", hasItem("Çaputçu Köyü")) //
                .statusCode(200)
              // "place.'place.name' " bu bilgiler Çaputçu köyü bu iteme sahim mi?

        ;
    }
    @Test
    public void bodyArrayHasSizeTest3(){
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body() // sonuçları yazdırmayı yapıyor yani responsu
                .body("places", hasSize(1)) // VERİLEN PATH DEKİ LİSTİN SİZE KONTROLU
                .statusCode(200)
        ;
    }
    @Test
    public void combingTest(){
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("places", hasSize(1))
                .body("places.state", hasItem("California"))
                .body("places[0].'place name' ", equalTo("Beverly Hills")) // aynı yerde birden fazla veri alınabiliyor.
                .statusCode(200)
        ;
    }
    @Test
    public void pathParamTest(){
        given()

                .pathParam("Country","us")
                .pathParam("ZipKod","90210")
                .log().uri()

                .when()
                .get("http://api.zippopotam.us/{Country}/{ZipKod}")

                .then()
                .log().body()
                .statusCode(200)   ;  }
    @Test
    public void pathParamTest2(){
        // 90210 dan 90250 kadar test sonuçlarından  place size nin jepsinde 1 geldiğini test ediniz
        for (int i = 90210; i <=90213 ; i++) {
            given()

                    .pathParam("Country","us")
                    .pathParam("ZipKod","90210")
                    .log().uri()

                    .when()
                    .get("http://api.zippopotam.us/{Country}/{ZipKod}")

                    .then()
                    .log().body()
                    .body("places", hasSize(1))
                    .statusCode(200)   ;  }
        }
    @Test
    public void queryParamTest(){
        given()

                .pathParam("page",1)
                .log().uri()

                .when()
                .get("https://gorest.co.in/public/v1/users")

                .then()
                .log().body()
                .body("meta.pagination.page ", equalTo(1))
                .statusCode(200)   ;  }
    @Test
    public void queryParamTest2() {
        for (int pageNo = 1; pageNo <=10; pageNo++) {

            given()

                    .pathParam("page", pageNo)
                    .log().uri()

                    .when()
                    .get("https://gorest.co.in/public/v1/users")

                    .then()
                    .log().body()
                    .body("meta.pagination.page ", equalTo(pageNo))
                    .statusCode(200);
        }
    }
    RequestSpecification requestSpaces;
    ResponseSpecification responseSpaces;
    @BeforeClass
    void setup(){
        baseURI="https://gorest.co.in/public/v1";
        requestSpaces =new RequestSpecBuilder()
                .log(LogDetail.URI)
                .setAccept(ContentType.JSON)
                .build();

        responseSpaces = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.BODY)
                .build();

    }
    @Test
    public void RequestSpecification() {

            given()

                    .pathParam("page", 1)
                    .spec(requestSpaces)


                    .when()
                    .get("/users") // url nin basında hppts yoksa yukardan alır.

                    .then()
                    .log().body()
                    .body("meta.pagination.page ", equalTo(1))
                    .spec(responseSpaces);
    }
    @Test
    public void extractinJsonPath(){
        String placeName=
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .extract().path("places[0]. ' place name' ")
        ;
        System.out.println("placeName = " + placeName);
    }
    @Test
    public void extractinJsonPathInt(){
        int limit=
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().path("meta.pagination.limit");
                         System.out.println("limit = " + limit);
                         Assert.assertEquals(limit,10, "test sonucu");

                ;
    }
    @Test
    public void extractinJsonPathInt2(){
        int id=
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().path("data[2].id");
                  System.out.println("id = " + id);
                  ;
    }
    @Test
    public void extractinJsonPathIntList(){
                List<Integer>idler=
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().path("data.id");
                ;
        System.out.println("idler = " + idler);
        Assert.assertTrue(idler.contains(3045));
    }
    @Test
    public void extractinJsonPathStringList(){
        List<String>isimler=
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().path("data.name");
        ;
        System.out.println("nameler = " + isimler);
        Assert.assertTrue(isimler.contains("Rageswari Saini"));
    }
    @Test
    public void extractinJsonPathResponseAl(){
               Response response =
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().response()
               ;
           List<Integer>idler= response.path("data.id");
        List<Integer>isimler= response.path("data.name");
        int limit= response.path("meta.pagination.limit");

        System.out.println("idler = " + idler);
        System.out.println("isimler = " + isimler);
        System.out.println("limit = " + limit);

    }
    @Test
    public void extractingJsonPOJO() {  // POJO : JSon Object i  (Plain Old Java Object)

        Location yer=
                given()

                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .extract().as(Location.class); // Location şablocnu
        ;

        System.out.println("yer. = " + yer);

        System.out.println("yer.getCountry() = " + yer.getCountry());
        System.out.println("yer.getPlaces().get(0).getPlacename() = " +
                yer.getPlaces().get(0).getPlacename());
    }


}


