package files;

public class Payload {

    public static String getAddPlacePayload() {
        return "{\n" +
                "    \"location\": {\n" +
                "        \"lat\": -38.383494,\n" +
                "        \"lng\": 33.427362\n" +
                "    },\n" +
                "    \"accuracy\": 50,\n" +
                "    \"name\": \"Rahul Shetty Academy\",\n" +
                "    \"phone_number\": \"(+91) 983 893 3937\",\n" +
                "    \"address\": \"29, side layout, cohen 09\",\n" +
                "    \"types\": [\n" +
                "        \"shoe park\",\n" +
                "        \"shop\"\n" +
                "    ],\n" +
                "    \"website\": \"http://rahulshettyacademy.com\",\n" +
                "    \"language\": \"French-IN\"\n" +
                "}";
    }

    public static String getUpdatePlacePayload(String placeId, String newAddress) {
        return "{\n" +
                "    \"place_id\": \"" + placeId + "\",\n" +
                "    \"address\": \"" + newAddress + "\",\n" +
                "    \"key\": \"qaclick123\"\n" +
                "}";
    }

    public static String getComplexJson(){
        return """
                  {
                  
                  "dashboard": {
                  
                  "purchaseAmount": 910,
                  
                  "website": "rahulshettyacademy.com"
                  
                  },
                  
                  "courses": [
                  
                  {
                  
                  "title": "Selenium Python",
                  
                  "price": 50,
                  
                  "copies": 6
                  
                  },
                  
                  {
                  
                  "title": "Cypress",
                  
                  "price": 40,
                  
                  "copies": 4
                  
                  },
                  
                  {
                  
                  "title": "RPA",
                  
                  "price": 45,
                  
                  "copies": 10
                  
                  }
                  
                  ]
                  
                  }""";
    }
}