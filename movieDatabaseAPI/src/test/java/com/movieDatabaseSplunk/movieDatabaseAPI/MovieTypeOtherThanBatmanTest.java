package com.movieDatabaseSplunk.movieDatabaseAPI;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
public class MovieTypeOtherThanBatmanTest {
	
	String currentDir = System.getProperty("user.dir")+File.separator+"Library//TestData.xlsx";

       @DataProvider(name = "InvalidParametersTest")
       public Object[][] fetchTestData() throws Exception{
    	   ExcelReaderPoi excel = new ExcelReaderPoi();
    	   return excel.readFilenSheet(currentDir, "MovieTypeNegativeCases");
       }
       
       @Test(priority=-1, dataProvider="InvalidParametersTest")
       public void verifyMovieAPIResponseWithInvalidParameters(Map mObj) {
    	   
    	   RestAssured.baseURI = "https://splunk.mocklab.io";
    	   String movieName =  (String) mObj.get("MovieName");


   		Response res = given().param("q", "movieName").header("Accept", "application/json").log().all().when()
   				.get(Resources.getAndPostData()).then().and().contentType(ContentType.JSON)
   				.and().log().all().extract().response();
   		
   		String StatusCodeExcel = (String) mObj.get("StatusCode");
		int expectedStatusCode = Integer.parseInt(StatusCodeExcel);
		int actualStatusCode = res.getStatusCode();

		
		Assert.assertEquals(actualStatusCode, expectedStatusCode, "The response code for movies other than batman should be 404 : (Assumption)");


    	   
       }

}
