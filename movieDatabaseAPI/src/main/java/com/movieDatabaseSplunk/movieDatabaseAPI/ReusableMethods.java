package com.movieDatabaseSplunk.movieDatabaseAPI;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReusableMethods {

	public static JsonPath rawtoJson(Response res) {
		String resp = res.asString();
		JsonPath js = new JsonPath(resp);
		return js;
	}

	public static Response getResponseData() {

		// Base URL
		RestAssured.baseURI = "https://splunk.mocklab.io";

		Response res = given().param("q", "batman").header("Accept", "application/json").log().all().when()
				.get(Resources.getAndPostData()).then().assertThat().statusCode(200).and().contentType(ContentType.JSON)
				.and().log().all().extract().response();

		return res;

	}

	public static void postMovieData() {

		// Base URL
		RestAssured.baseURI = "https://splunk.mocklab.io";

		given().header("Content-Type", "application/json").body(payLoad.getPostData()).when()
				.post(Resources.getAndPostData()).then().assertThat().statusCode(200).and()
				.log().all();

	}

}
