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

	@Test
	public void testSortingRequirement() {

		Response res = ReusableMethods.getResponseData();

		JsonPath js = ReusableMethods.rawtoJson(res);

		int response_arr_size = js.get("results.size()");
		boolean nullValuesPresent = false;
		ArrayList<Integer> movie_id_list_with_null_genre_ids = new ArrayList<Integer>();
		ArrayList<Integer> movie_id_list_with_non_null_genre_ids = new ArrayList<Integer>();

		// Verifying whether movies with genre_ids null is first in the response
		for (int k = 0; k < response_arr_size; k++) {

			if ((Integer) js.get("results[" + k + "].genre_ids.size()") == 0) {
				nullValuesPresent = true;
			}
		}

		if (nullValuesPresent == true) {
			for (int i = 0; i < response_arr_size; i++) {
				Assert.assertEquals(js.get("results[" + i + "].genre_ids.size()"), null,
						"genre_ids with null values are not first in response : " + "genre_id"
								+ js.get("results[" + i + "].genre_ids"));
			}
		}

		// Adding the list of movies with null genre ids
		for (int i = 0; i < response_arr_size; i++) {
			int genre_ids_check = js.get("results[" + i + "].genre_ids.size()");
			int movie_id = js.get("results[" + i + "].id");
			if (genre_ids_check == 0) {
				movie_id_list_with_null_genre_ids.add(movie_id);

			}

		}

		Collections.sort(movie_id_list_with_null_genre_ids);

		// Validating whether a null genre id movies are sorted with ids.
		for (int i = 0; i < movie_id_list_with_null_genre_ids.size(); i++) {
			int genre_ids_check = js.get("results[" + i + "].genre_ids.size()");
			if (genre_ids_check == 0) {
				Assert.assertEquals(js.get("results[" + i + "].id"), movie_id_list_with_null_genre_ids.get(i),
						"Movies with null genre_ids are not sorted with ids");
			}
		}

		// Adding the list of movies with non-null genre ids

		for (int i = 0; i < response_arr_size; i++) {
			int genre_ids_check = js.get("results[" + i + "].genre_ids.size()");
			int movie_id = js.get("results[" + i + "].id");
			if (genre_ids_check != 0) {
				movie_id_list_with_non_null_genre_ids.add(movie_id);
			}
		}

		Collections.sort(movie_id_list_with_non_null_genre_ids);

		// Validating whether a non-null genre id movies are sorted with ids.

		for (int i = 0; i < movie_id_list_with_non_null_genre_ids.size(); i++) {
			int genre_ids_check = js.get("results[" + i + "].genre_ids.size()");
			if (genre_ids_check != 0) {
				Assert.assertEquals(js.get("results[" + i + "].id"), movie_id_list_with_non_null_genre_ids.get(i),
						"Movies with non-null genre_ids are not sorted with ids");
			}
		}

	}
}
