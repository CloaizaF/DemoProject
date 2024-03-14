package org.example;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

    public static void main(String[] args){

        JsonPath complexJson = new JsonPath(Payload.getComplexJson());

        //Print No courses returned by API
        int coursesCount = complexJson.getInt("courses.size()");
        System.out.println(coursesCount);

        //Print Purchase Amount
        int totalAmount = complexJson.getInt("dashboard.purchaseAmount");
        System.out.println(totalAmount);

        //Print first course's title
        String firstCourseTitle = complexJson.getString("courses[0].title");
        System.out.println(firstCourseTitle);

        //Print all courses titles and their respective prices
        int totalAmountCal = 0;
        for(int i = 0; i < coursesCount; i++){
            String courseTitle = complexJson.get(String.format("courses[%s].title", i));
            int coursePrice = complexJson.get(String.format("courses[%s].price", i));
            int courseSoldCopies = complexJson.get(String.format("courses[%s].copies", i));
            totalAmountCal +=  coursePrice * courseSoldCopies;
            System.out.println(courseTitle + " " + coursePrice);
        }

        //Print if amounts are equal
        if(totalAmountCal == totalAmount){
            System.out.println("Amounts are equal");
        } else {
            System.out.println("Amounts are not equal");
        }

    }
}
