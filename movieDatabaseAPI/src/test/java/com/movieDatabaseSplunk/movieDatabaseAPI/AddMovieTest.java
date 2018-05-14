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

	@Test
	public void addMovieToDatabase() {

		// Getting the size of the response before making a POST request
		Response res = ReusableMethods.getResponseData();

		JsonPath js = ReusableMethods.rawtoJson(res);

		assertTrue(MoviesInformation.verifyMoviesAddedAfterPost(js),
				"The movie added by POST is not getting displayed in GET/movie API");

	}

}
