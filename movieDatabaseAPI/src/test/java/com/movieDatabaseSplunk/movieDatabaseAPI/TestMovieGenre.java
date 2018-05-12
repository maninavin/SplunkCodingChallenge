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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestMovieGenre {

Properties prop = new Properties();

	
	@BeforeTest
	public void getData() throws IOException {
		FileInputStream fis  = new FileInputStream("C:\\Users\\M.Subramaniam\\eclipse-workspace\\Splunk_Movie_Challenge\\movieDatabaseAPI\\src\\test\\java\\files\\env.properties");
		prop.load(fis);
		
	}
	
	@Test
	public void validateMovieImage() {
		
		//Base URL
		RestAssured.baseURI = prop.getProperty("HOST");
		
		Response res = given().
		param("q", prop.getProperty("MOVIE_TYPE")).
		header("Accept", prop.getProperty("ACCEPT_TYPE")).
		when().get(Resources.getAndPostData()).
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().body("results[0].genre_ids[0]",equalTo(null)).and().extract().response();
		
		JsonPath js = ReusableMethods.rawtoJson(res);
		int response_arr = js.get("results.size()");
		ArrayList<Integer> genre_ids_list = new ArrayList<Integer>();
		ArrayList<Integer> genre_ids_sorted = new ArrayList<Integer>();
		
		for (int i=0;i<response_arr;i++) {
			  int genre_ids_size = js.get("results["+i+"].genre_ids.size()");
			  for(int j=0;j<genre_ids_size;j++) {
				  int genre_ids =  js.get("results["+i+"].genre_ids["+j+"]") ;
				  genre_ids_list.add(genre_ids);
			  }
			  
		
		}
		System.out.println(genre_ids_list);
		Collections.sort(genre_ids_list);
		genre_ids_sorted.addAll(genre_ids_list);
		  System.out.println(genre_ids_sorted);
//Need to add 3 asserts for 3 scenarios
// 1. First genre id should be null
// 2. If two multiple are null then sort by ascending id

// 3. For not null values of genre id sort by ascending order of id
		  
// NOTE :- Did wrong sorting I need to sort bu id if there are multiple null values/no null values.
		
	}
}


