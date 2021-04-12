package br.ce.wcaquino.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
		.when()
		.get("/todo")
		.then()
		.statusCode(200);
		
	}
	
	@Test
	public void deveAdicionarTarefaComSucesso() {
		
		String json = "{\"task\":\"teste task api\",\"dueDate\":\"2030-12-30\"}";
		
		RestAssured.given()
		.contentType(ContentType.JSON)
		.body(json)
		.when()
		.post("/todo")
		.then()
		.statusCode(201);
		
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		
		String json = "{\"task\":\"teste task api\",\"dueDate\":\"2010-12-30\"}";
		
		RestAssured.given()
		.contentType(ContentType.JSON)
		.body(json)
		.when()
		.post("/todo")
		.then()
		.log().all()
		.statusCode(400)
		.body("message", CoreMatchers.is("Due date must not be in past"));
		
	}
	
}
