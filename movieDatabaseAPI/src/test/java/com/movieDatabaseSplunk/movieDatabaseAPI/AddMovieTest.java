package com.movieDatabaseSplunk.movieDatabaseAPI;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AddMovieTest {
	
Properties prop = new Properties();

	
	@BeforeTest
	public void getData() throws IOException {
		FileInputStream fis  = new FileInputStream("C:\\Users\\M.Subramaniam\\eclipse-workspace\\Splunk_Movie_Challenge\\movieDatabaseAPI\\src\\test\\java\\files\\env.properties");
		prop.load(fis);
		
	}
	
	@Test
	public void addMovieToDatabase() {
		
		RestAssured.baseURI= prop.getProperty("HOST");
		
	    Response res = 	
	    given().
		header("Content-Type", prop.getProperty("CONTENT_TYPE_JSON")).
		body(payLoad.getPostData()).
		when().post(Resources.getAndPostData()).then().assertThat().statusCode(200).and().
		extract().response();
	
	    JsonPath js = ReusableMethods.rawtoJson(res);
	    String test = js.get("message");
		
	}

}
