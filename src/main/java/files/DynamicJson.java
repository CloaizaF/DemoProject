package files;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {

    @Test(dataProvider = "BooksData")
    public void addBook(String isbn, String aisle) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        //Add a book
        String addBookResponse = given().log().all().header("Content-Type", "application/json")
                .body(Payload.AddBook(isbn, aisle))
                .when().post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath addBookResponseJson = new JsonPath(addBookResponse);
        String bookAddedID = addBookResponseJson.get("ID");
        System.out.println(bookAddedID);

        //Delete a book


    }

    //I can link this annotation to the test in order to provide it data to run multiple test scenarios
    @DataProvider(name="BooksData")
    public Object[][] getBooksData(){
        return new Object[][] {{"isbn1", "101"}, {"isbn2", "202"}, {"isbn3", "303"}};
    }
}
