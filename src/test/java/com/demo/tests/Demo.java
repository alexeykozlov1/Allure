package com.demo.tests;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Demo {
    @Test
    public void getTestExtract() {
        String request =
                given()
                        .header("Content-Type", "application-json")
                        .when()
                        .get("https://postman-echo.com/get?test=123")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("headers.x-forwarded-port");
        Assert.assertEquals(request,"443");


    }

    @Test
    public void postTest() {
        String request =
                given()
                        .header("Content-Type", "application-json")
                        .body(new File("src/test/java/com/demo/json/GetResponse.json"))
                        .when()
                        .post("https://postman-echo.com/post")
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();

        try {
            FileWriter file = new FileWriter("src/test/java/com/demo/json/PostResponse.json");
            file.write(request);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateJson() throws IOException, ParseException {
        FileReader reader = new FileReader("src/test/java/com/demo/json/PostResponse.json");
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
        System.out.println(jsonObject);

        JSONObject idObj = (
                (JSONObject) (
                        jsonObject.get("headers"))
        );

        idObj.put("accept-encoding", "From Excel");
        System.out.println("After ID value updated : " + jsonObject);

        try {
            FileWriter file = new FileWriter("src/test/java/com/demo/json/PostResponse.json");
            file.write(String.valueOf(jsonObject));
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void putTest() {
        String request =
                given()
                        .header("Content-Type", "application-json")
                        .body(new File("src/test/java/com/demo/json/PostResponse.json"))
                        .when()
                        .put("https://postman-echo.com/put")
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();

        try {
            FileWriter file = new FileWriter("src/test/java/com/demo/json/PostResponse.json");
            file.write(request);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteTest() {
        String request =
                given()
                        .header("Content-Type", "application-json")
                        .when()
                        .delete("https://postman-echo.com/delete")
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();

        try {
            FileWriter file = new FileWriter("src/test/java/com/demo/json/DeleteResponse.json");
            file.write(request);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
