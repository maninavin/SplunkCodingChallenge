package com.movieDatabaseSplunk.movieDatabaseAPI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PosterPathLinkValidation {
	


	public static boolean validatePosterPathLink(String posterPathLink) throws IOException,MalformedURLException {
		
		try {
			if(posterPathLink==null) {
				return true;
			}
			URL url = new URL(posterPathLink);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			int code = connection.getResponseCode();
			System.out.println(code);
			
			if(code==200) {
			  return true;
			}
			return false;
		}
		catch(Exception e){
			 return false;
		}
		
}
}
