package restassured;

import dto.ContactDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import okhttp.BaseOkhttp;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DeleteContactByIdRA {
    String id;
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWFyYUBnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTc0NDYxNTgyMSwiaWF0IjoxNzQ0MDE1ODIxfQ.8ZvoDKmswzqmbYZpTLEJx3EVJcQQmbEiK0ccySt2Roo";


    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";

        int i = new Random().nextInt(1000) + 1000;
        ContactDto contact = ContactDto.builder()
                .name("Maya")
                .lastName("Dow")
                .email("maya" + i + "@gmail.com")
                .phone("12345565" + i)
                .address("TA")
                .description("Friend")
                .build();
        String message = given()
                .body(contact)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                //"Contact was added! ID: a4a33d33-b00e-4049-8570-dfd07aace9c7"
                .extract()
                .path("message");
        String[] idh = message.split(": ");
        id = idh[1];

    }

    @Test
    public void deleteContactByIdSuccess() {
        given()
                .header("Authorization", token)
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", equalTo("Contact was deleted!"));


    }

    @Test
    public void deleteContactByIdWrongToken() {
       Object inf= given()
                .header("Authorization","trfg")
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(401)
                .assertThat().body("error", equalTo("Unauthorized"))
                .extract()
                .path("message");
        System.out.println("INFO :"+inf.toString());

    }
}