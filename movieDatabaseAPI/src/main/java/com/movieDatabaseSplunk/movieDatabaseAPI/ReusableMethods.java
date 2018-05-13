package com.movieDatabaseSplunk.movieDatabaseAPI;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReusableMethods {

	public static JsonPath rawtoJson(Response res) {
		String resp = res.asString();
		JsonPath js = new JsonPath(resp);
		return js;
	}
}

     