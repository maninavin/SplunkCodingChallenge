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

	@Test
	public void testPosterPathLinks() throws IOException {

		Response res = ReusableMethods.getResponseData();

		JsonPath js = ReusableMethods.rawtoJson(res);
		int response_arr_size = js.get("results.size()");

		for (int i = 0; i < response_arr_size; i++) {
			String posterPathLink = js.get("results[" + i + "].poster_path");
			System.out.println(posterPathLink);
			assertTrue(PosterPathLinkValidation.validatePosterPathLink(posterPathLink),
					"The poster path link is invalid : " + posterPathLink);
		}

	}
}
