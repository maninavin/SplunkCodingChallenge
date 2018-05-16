package com.movieDatabaseSplunk.movieDatabaseAPI;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

public class checkNoOfRecordsByCountTest {
	
	String currentDir = System.getProperty("user.dir") + File.separator + "Library//TestData.xlsx";

	@DataProvider(name = "AddingCountPositiveCasesTest")
	public Object[][] fetchPositiveTestData() throws Exception {
		ExcelReaderPoi excel = new ExcelReaderPoi();
		return excel.readFilenSheet(currentDir, "AddingCountPositiveCases");
	}

	@Test(priority = -1, dataProvider = "AddingCountPositiveCasesTest")
	public void verifyMovieAPIResponseByCountPositiveCase(Map mObj) {

		RestAssured.baseURI = "https://splunk.mocklab.io";
		String countofMovies = (String) mObj.get("Count");
		String expectedResponseSize = (String) mObj.get("ResponseSize");

		Response res = given().param("q", "batman").and().param("count", "countofMovies").and()
				.header("Accept", "application/json").log().all().when().get(Resources.getAndPostData()).then()
				.assertThat().statusCode(200).and().contentType(ContentType.JSON).and().log().all().extract()
				.response();

		JsonPath js = ReusableMethods.rawtoJson(res);
		String actualResponseSize = js.get("results.size()").toString();

		Assert.assertEquals(actualResponseSize, expectedResponseSize,
				"The response is not as per the count specified.");
	}
   		
   		
       
       
       
       
   		
	@DataProvider(name = "AddingCountNegativeCasesTest")
	public Object[][] fetchNegativeTestData() throws Exception {
		ExcelReaderPoi excel = new ExcelReaderPoi();
		return excel.readFilenSheet(currentDir, "AddingCountNegativeCases");
	}

	@Test(dataProvider = "AddingCountNegativeCasesTest")
	public void verifyMovieAPIResponseByCountNegativeCase(Map mObj) {

		RestAssured.baseURI = "https://splunk.mocklab.io";
		String countofMovies = (String) mObj.get("Count");
		String StatusCodeExcel = (String) mObj.get("StatusCode");
		int expectedStatusCode = Integer.parseInt(StatusCodeExcel);

		Response res = given().param("q", "batman").and().param("count", "countofMovies").and()
				.header("Accept", "application/json").log().all().when().get(Resources.getAndPostData()).then()
				.assertThat().statusCode(200).and().contentType(ContentType.JSON).and().log().all().extract()
				.response();
		int actualStatusCode = res.getStatusCode();

		Assert.assertEquals(actualStatusCode, expectedStatusCode, "The response code with invalid count should be 404 : (Assumption)");

	}

}
