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

public class MovieTitleMatch {

Properties prop = new Properties();

	
	@BeforeTest
	public void getData() throws IOException {
		FileInputStream fis  = new FileInputStream("C:\\Users\\M.Subramaniam\\eclipse-workspace\\Splunk_Movie_Challenge\\movieDatabaseAPI\\src\\test\\java\\files\\env.properties");
		prop.load(fis);
		
	}
	@Test
	public void testMoviesTitleMatch() {
		
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
		
		ArrayList<String> movie_list = new ArrayList<String>();
		
       for (int i=0 ; i<response_arr; i++) {
    	   String movie_title = js.get("results["+i+"].title");
    	   movie_list.add(movie_title);   

    	 }
       System.out.println(movie_list);
       for (int j=0 ; j<movie_list.size(); j++) {
    	   for(int k=0 ;k<movie_list.size(); k++) {
    	   if(movie_list.get(j).contains(movie_list.get(k)) && movie_list.get(j)!=movie_list.get(k)) {
    		   System.out.println("String 1: " + movie_list.get(j) + " String 2: " + movie_list.get(k)); 
    		   count++;
    	   }
    	   
       }
  }
       
       
       assertTrue(count>=2,"There are no two movies whose title contains title of another.");

       

}
       

}


