package com.movieDatabaseSplunk.movieDatabaseAPI;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class InvalidParameterTest {
	
//String currentDir = System.getProperty("C:\\Users\\M.Subramaniam\\eclipse-workspace\\Splunk_Movie_Challenge\\movieDatabaseAPI\\Library\\TestData.xlsx");
	String currentDir = System.getProperty("user.dir")+File.separator+"Library\\TestData.xlsx";

       @DataProvider(name = "InvalidParametersTest")
       public Object[][] fetchPositiveTestData() throws Exception{
    	   ExcelReaderPoi excel = new ExcelReaderPoi();
    	   return excel.readFilenSheet(currentDir, "InvalidParameters");
       }
       
       @Test(priority=-1, dataProvider="InvalidParametersTest")
       public void verifyMovieAPIResponseWithInvalidParameters(Map mObj) {
    	   
    	   RestAssured.baseURI = "https://splunk.mocklab.io";
    	   String movieName =  (String) mObj.get("MovieName");


   		Response res = given().param("q", "movieName").header("Accept", "application/json").log().all().when()
   				.get(Resources.getAndPostData()).then().assertThat().statusCode(404).and().contentType(ContentType.JSON)
   				.and().log().all().extract().response();


    	   
       }

}
