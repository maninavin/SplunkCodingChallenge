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

public class TestSumMovieGenre {

Properties prop = new Properties();

	
	@BeforeTest
	public void getData() throws IOException {
		FileInputStream fis  = new FileInputStream("C:\\Users\\M.Subramaniam\\eclipse-workspace\\Splunk_Movie_Challenge\\movieDatabaseAPI\\src\\test\\java\\files\\env.properties");
		prop.load(fis);
		
	}
	@Test
	public void testSumOfGenreIds() {
		
		//Base URL
		RestAssured.baseURI = prop.getProperty("HOST");
		
		Response res = given().
		param("q", prop.getProperty("MOVIE_TYPE")).
		header("Accept", prop.getProperty("ACCEPT_TYPE")).
		when().get(Resources.getAndPostData()).
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().extract().response();
		
		JsonPath js = ReusableMethods.rawtoJson(res);
		int response_arr = js.get("results.size()");
		int count = 0;
		
		for (int i=0;i<response_arr;i++) {
		  int genre_ids_size = js.get("results["+i+"].genre_ids.size()");
		  int sum_of_genre_ids = 0;
		  for(int j=0;j<genre_ids_size;j++) {
			  int sum_helper =  js.get("results["+i+"].genre_ids["+j+"]") ;
			  sum_of_genre_ids = sum_of_genre_ids + sum_helper;			  
		  }
		  if(sum_of_genre_ids > 400) {
			  count++;
		  }
		  //System.out.println(sum_of_genre_ids);
		}
		
        assertTrue(count<=7,"There are more than 7 movies with genre_ids sum greater than 400");

		
	}

}
