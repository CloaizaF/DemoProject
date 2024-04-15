package files;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.File;

import static io.restassured.RestAssured.given;

public class JiraTest {

    public static void main(String[] args) {

        RestAssured.baseURI = "http://localhost:8080";

        //SessionFilter class allows me to remember the response and save it in the sessionFilter instance
        //This can help for later use
        SessionFilter session = new SessionFilter();

        //Login
        //We handle the HTTPS certification by adding .relaxedHTTPSValidation() to the given() method
        String cookieResponse = given()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"username\": \"username\",\n" +
                        "    \"password\": \"password\"\n" +
                        "}")
                .log().all().filter(session)
                .when().post("/rest/auth/1/session")
                .then().log().all().extract().response().asString();

        String issueId = "10100";
        String commentMessage = "This is a comment";

        //Add comment to issue
        String addCommentResponse = given().pathParam("issueIdOrKey", issueId).log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"body\": \"" + commentMessage + "\",\n" +
                        "    \"visibility\": {\n" +
                        "        \"type\": \"role\",\n" +
                        "        \"value\": \"Administrators\"\n" +
                        "    }\n" +
                        "}")
                .filter(session)
                .when().post("/rest/api/2/issue/{issueIdOrKey}/comment")
                .then().log().all().assertThat().statusCode(201).extract().asString();

        JsonPath addCommentResponseJson = new JsonPath(addCommentResponse);
        String commentId = addCommentResponseJson.getString("id");

        //Add attachment
        //We send files in RestAssured with MultiPart method
        given().pathParam("issueIdOrKey", issueId).log().all()
                .header("X-Atlassian-Token", "no-check").filter(session)
                .header("Content-Type", "multipart/form-data")
                .multiPart("file", new File("C:\\Users\\loaiz\\IdeaProjects\\DemoProject\\src\\main\\java\\files\\jira.txt"))
                .when().post("/rest/api/2/issue/{issueIdOrKey}/attachments")
                .then().log().all().assertThat().statusCode(200);

        //Get Issue
        String getIssueResponse = given().pathParam("issueIdOrKey", issueId).log().all()
                .filter(session)
                .queryParam("fields", "comment")
                .when().get("/rest/api/2/issue/{issueIdOrKey}")
                .then().log().all().extract().response().asString();

        //Assert if the comment created has the correct message
        JsonPath getIssueResponseJson = new JsonPath(getIssueResponse);
        int commentsCount = getIssueResponseJson.getInt("fields.comment.comments.size()");
        String commentIdFound = "";
        String commentMessageFound = "";

        for (int i = 0; i < commentsCount; i++) {
            commentIdFound = getIssueResponseJson.get(String.format("fields.comment.comments[%s].id", i)).toString();
            if (commentId.equals(commentIdFound)) {
                commentMessageFound = getIssueResponseJson.get(String.format("fields.comment.comments[%s].body", i)).toString();
                Assert.assertEquals(commentMessage, commentMessageFound);
            }
        }
    }
}
