package com.movieDatabaseSplunk.movieDatabaseAPI;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import junit.framework.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertTrue;

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
		
		Response res =given().
		param("q", prop.getProperty("MOVIE_TYPE")).
		header("Accept", prop.getProperty("ACCEPT_TYPE")).
		when().get(Resources.getAndPostData()).
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().response();
		
		JsonPath js = ReusableMethods.rawtoJson(res);
		int beforePostMovieSize = js.get("results.size()");	

		
	     	
	    given().
		header("Content-Type", prop.getProperty("CONTENT_TYPE_JSON")).
		body(payLoad.getPostData()).
		when().post(Resources.getAndPostData()).then().assertThat().statusCode(200);

	    
	    // Checking the posted movie is seen in the GET/movie API
	    
			
			//Base URL
			RestAssured.baseURI = prop.getProperty("HOST");
			Response res1 = 	
		    given().
			param("q", prop.getProperty("MOVIE_TYPE")).
			header("Accept", prop.getProperty("ACCEPT_TYPE")).
			when().get(Resources.getAndPostData()).
			then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().extract().response();
			
			JsonPath js1 = ReusableMethods.rawtoJson(res1);
			js1.get("results[0].title");
			int afterPostMovieSize = (js1.get("results.size()"));
			assertTrue(afterPostMovieSize > beforePostMovieSize,"The movie added by POST is not getting displayed in GET/movie API");
			
		
	}

}

