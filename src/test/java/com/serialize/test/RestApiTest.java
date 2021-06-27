package com.serialize.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.pojo.AddResource;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

import static io.restassured.RestAssured.*;

public class RestApiTest {

	public AddResource postResponse;
	//POST call
	@Test(dataProvider = "resourceData")
	public void createResource(String title, String body, int userId) {

		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		AddResource res = new AddResource();
		res.setTitle(title);
		res.setBody(body);
		res.setUserId(userId);

		postResponse = given().log().all().header("Content-type", "application/json").body(res).expect().defaultParser(Parser.JSON).when()
				.post("/posts").then().assertThat().statusCode(201).extract().response().as(AddResource.class);
		
		System.out.println("Resource title :" +postResponse.getTitle());
		System.out.println("Resource body :" +postResponse.getBody());
		System.out.println("Resource User Id :" +postResponse.getUserId());
		System.out.println("Resource id :" +postResponse.getId());
	
	}

	@DataProvider(name = "resourceData")
	public Object[][] getData() {

		return new Object[][] { { "Mercedes benz", "G-Wagon", 5 }, { "Lamborghini", "Aventador", 6 }};

	}

	//DELETE call
	@Test
	public void deleteResource() {

		String deleteResponse = given().log().all().when().delete("/posts/" +postResponse.getUserId()).then().assertThat().statusCode(200)
				.extract().response().asString();
		System.out.println(deleteResponse);

	}

}
