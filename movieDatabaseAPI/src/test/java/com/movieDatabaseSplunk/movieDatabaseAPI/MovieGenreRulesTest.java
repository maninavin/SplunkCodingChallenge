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
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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

public class MovieGenreRulesTest {

	@Test
	public void testSortingRequirement() {

		Response res = ReusableMethods.getResponseData();

		JsonPath js = ReusableMethods.rawtoJson(res);
		
		assertTrue(MoviesInformationHelper.verifyFirstGenreIdsNull(js),"First genre_ids is not null");
		


		assertTrue(MoviesInformationHelper.verifyNullGenreIdsAreSortedByIds(js),"Null genre_ids are not sorted by ids");

		

		assertTrue(MoviesInformationHelper.verifyNonNullGenreIdsSortedByIds(js),"Non null genre_ids are sorted by ids");
	}
}
