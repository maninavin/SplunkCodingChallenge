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
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestPalindromeMovieTitle {

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
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().extract().response();
		
		JsonPath js = ReusableMethods.rawtoJson(res);
		int response_arr = js.get("results.size()");
		int count = 0;
		
       for (int i=0 ; i<response_arr; i++) {
    	   String movie_title = js.get("results["+i+"].title");
    	   String words[] = movie_title.split(" ");
    	   for( int j=0;j<words.length;j++) {
    		   StringBuilder reversed_movie_word_in_title = new StringBuilder();
    		   reversed_movie_word_in_title.append(words[j].toLowerCase());
    		  String reversed_movie_word_in_title_first = reversed_movie_word_in_title.reverse().toString().toLowerCase();
    		   if(reversed_movie_word_in_title_first.equals(words[j].toLowerCase())) {
    			   count++;
    		   }
    	    }

       }
       
       System.out.println(count);
       Assert.assertTrue(count>=1,"There are no movies which contains palindrome in it.");

   }

}
