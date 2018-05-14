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
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MoviesInformation {

	/**
	 * This method verifies whether there are any duplicate images present in the
	 * Movies API Usage: assertTrue(verifyDuplicateImages) == True
	 * 
	 * @author M.Subramaniam
	 */

	public static boolean verifyDuplicateImages(JsonPath js) {
		int response_arr = js.get("results.size()");

		ArrayList<String> imagesList = new ArrayList<String>();
		Set<String> imagesSet = new HashSet<String>();

		for (int i = 0; i < response_arr; i++) {
			String movieImages = js.get("results[" + i + "].poster_path");
			System.out.println(movieImages);
			imagesList.add(movieImages);
		}

		for (int i = 0; i < imagesList.size(); i++) {

			imagesSet.add(imagesList.get(i));

		}

		if (imagesSet.size() == imagesList.size()) {
			return true;
		} else {
			return false;
		}
	}
	
	

	/**
	 * This method verifies whether movies are displayed in GET movies request after
	 * the movie is added by POST movies request. 
	 * Movies API Usage:
	 * assertTrue(verifyMoviesAddedAfterPost) == True
	 * 
	 * @author M.Subramaniam
	 */

	public static boolean verifyMoviesAddedAfterPost(JsonPath js) {

		int beforePostMovieSize = js.get("results.size()");

		// Adding the movie to the database (POST)
		ReusableMethods.postMovieData();

		// Checking the posted movie is seen in the GET/movie API by verifying the new

		Response res1 = ReusableMethods.getResponseData();

		JsonPath js1 = ReusableMethods.rawtoJson(res1);
		js1.get("results[0].title");
		int afterPostMovieSize = (js1.get("results.size()"));

		if (afterPostMovieSize > beforePostMovieSize) {
			return true;
		} else {
			return false;
		}

	}
	
	

	/**
	 * This method verifies whether there are atleast two movies whose title
	 * contains the title of another movies.
	 * Movies API Usage:
	 * assertTrue(verifyMoviesTitlesMatch) == True
	 * 
	 * @author M.Subramaniam
	 */

	public static boolean verifyMoviesTitlesMatch(JsonPath js) {
		int response_arr = js.get("results.size()");
		int count = 0;

		ArrayList<String> movie_list = new ArrayList<String>();

		for (int i = 0; i < response_arr; i++) {
			String movie_title = js.get("results[" + i + "].title");
			movie_list.add(movie_title);

		}
		System.out.println(movie_list);
		for (int j = 0; j < movie_list.size(); j++) {
			for (int k = 0; k < movie_list.size(); k++) {
				if (movie_list.get(j).contains(movie_list.get(k)) && movie_list.get(j) != movie_list.get(k)) {
					System.out.println("String 1: " + movie_list.get(j) + " String 2: " + movie_list.get(k));
					count++;
				}

			}
		}

		if (count >= 2) {
			return true;
		} else {
			return false;
		}

	}
	
	
	/**
	 * This method verifies whether there is at least one movie in the database which has palindrome in it.
	 * contains the title of another movies.
	 * Movies API Usage:
	 * assertTrue(verifyPalindromeMovieTitles) == True
	 * 
	 * @author M.Subramaniam
	 */
	
	
	public static boolean verifyPalindromeMovieTitles(JsonPath js) {
		int response_arr = js.get("results.size()");
		int count = 0;

		for (int i = 0; i < response_arr; i++) {
			String movie_title = js.get("results[" + i + "].title");
			String words[] = movie_title.split(" ");
			for (int j = 0; j < words.length; j++) {
				StringBuilder reversed_movie_word_in_title = new StringBuilder();
				reversed_movie_word_in_title.append(words[j].toLowerCase());
				String reversed_movie_word_in_title_first = reversed_movie_word_in_title.reverse().toString()
						.toLowerCase();
				if (reversed_movie_word_in_title_first.equals(words[j].toLowerCase())) {
					count++;
				}
			}

		}

		System.out.println(count);
		if (count >= 1) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	
	/**
	 * This method verifies whether there is no more than 7 instances where sum of genre_ids > 400.
	 * contains the title of another movies.
	 * Movies API Usage:
	 * assertTrue(verifySumOfGenreIds) == True
	 * 
	 * @author M.Subramaniam
	 */
	
	
	public static boolean verifySumOfGenreIds(JsonPath js) {
		int response_arr = js.get("results.size()");
		int count = 0;

		for (int i = 0; i < response_arr; i++) {
			int genre_ids_size = js.get("results[" + i + "].genre_ids.size()");
			int sum_of_genre_ids = 0;
			for (int j = 0; j < genre_ids_size; j++) {
				int sum_helper = js.get("results[" + i + "].genre_ids[" + j + "]");
				sum_of_genre_ids = sum_of_genre_ids + sum_helper;
			}
			if (sum_of_genre_ids > 400) {
				count++;
			}
		}
		
		if(count<=7) {
			return true;
		}
		else {
			return false;
		}
	}
	
}
	
