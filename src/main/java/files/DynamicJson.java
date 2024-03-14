package files;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {

    @Test
    public void addBook() {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        String addBookResponse = given().log().all().header("Content-Type", "application/json")
                .body(Payload.AddBook("lovo", "0011"))
                .when().post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath addBookResponseJson = new JsonPath(addBookResponse);
        String bookAddedID = addBookResponseJson.get("ID");
        System.out.println(bookAddedID);
    }
}
