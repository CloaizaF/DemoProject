package org.example;

import io.restassured.RestAssured;
import files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Basics {

    public static void main(String[] args) {

        //Validate if AddPlace API is working as expected
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String addPlaceResponse = given().log().all().queryParam("key", "qaclick123").
                header("Content-Type", "application/json").body(payload.getAddPlacePayload())
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

        //AddPlace -> Update Place with New Address -> Get Place to validate if New Address is present in response.
        JsonPath jsonAddPlaceResponse = new JsonPath(addPlaceResponse);
        String placeId = jsonAddPlaceResponse.getString("place_id");

        String newAddress = "70 Summer walk, USA";

        //Validate if UpdatePlace API is working as expected
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(payload.getUpdatePlacePayload(placeId, newAddress))
                .when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));


        //Validate if GetPlace API is working as expected
        String getPlaceResponse = given().log().all().queryParams("place_id", placeId, "key", "qaclick123")
                .when().get("maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath jsonGetPlaceResponse = new JsonPath(getPlaceResponse);
        String actualAddress = jsonGetPlaceResponse.getString("address");

        //JUnit, TestNG
        Assert.assertEquals(newAddress, actualAddress);


    }
}