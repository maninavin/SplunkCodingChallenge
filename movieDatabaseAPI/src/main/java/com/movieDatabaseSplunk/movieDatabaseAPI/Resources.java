package com.movieDatabaseSplunk.movieDatabaseAPI;

import io.restassured.RestAssured;

public  class Resources {
	
	public static String getAndPostData() {
		String res = "movies";
		return res;
	}
	
	public static void resetBaseURI() {
		RestAssured.baseURI = null;
	}

}
