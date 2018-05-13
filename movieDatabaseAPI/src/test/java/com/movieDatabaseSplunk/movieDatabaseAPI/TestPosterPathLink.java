package com.movieDatabaseSplunk.movieDatabaseAPI;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import com.movieDatabaseSplunk.movieDatabaseAPI.*;

import groovyjarjarantlr.collections.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestPosterPathLink {
	
	Properties prop = new Properties();

	
	@BeforeTest
	public void getData() throws IOException {
		FileInputStream fis  = new FileInputStream("C:\\Users\\M.Subramaniam\\eclipse-workspace\\Splunk_Movie_Challenge\\movieDatabaseAPI\\src\\test\\java\\files\\env.properties");
		prop.load(fis);
		
	}
	
	@Test
	public void testPosterPathLinks() throws IOException {
		
		//Base URL
		RestAssured.baseURI = prop.getProperty("HOST");
		
		Response res = given().
		param("q", prop.getProperty("MOVIE_TYPE")).
		header("Accept", prop.getProperty("ACCEPT_TYPE")).
		when().get(Resources.getAndPostData()).
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		body("results[0].poster_path",equalTo("https://www.dropbox.com/s/8i8v4ak8tnp03w4/action-blur-electronics-247932.jpg?dl=0")).and().
		extract().response();
		
		JsonPath js = ReusableMethods.rawtoJson(res);
		int response_arr_size = js.get("results.size()");
		
		for(int i=0;i<response_arr_size;i++) {
			String posterPathLink = js.get("results["+i+"].poster_path");
			System.out.println(posterPathLink);
			assertTrue(PosterPathLinkValidation.validatePosterPathLink(posterPathLink),"The poster path link is invalid : "+posterPathLink);
		}
			
	}
}
