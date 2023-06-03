package com.todo.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.constants.TodoConstants;
import com.todo.model.TodoObject;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TodoApplicationTests {
	
	@LocalServerPort
	private int portNumber;
	
	private String baseUrl = "http://localhost";

	private static RestTemplate restTemplate;

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}
	
	@BeforeEach
	public void beforeSetup() {
		baseUrl = baseUrl+":"+portNumber+"/todo";
	}
	
	@Test
	public void testAddMovie() {
		TodoObject todoObject = new TodoObject();
		todoObject.setTaskName("Code");
		String status = restTemplate.postForObject(baseUrl+"/addTask", todoObject, String.class);
		assertThat(status).hasToString(TodoConstants.TASK_ADDED_SUCCESSFULLY);
	}
	
	@Test
	public void testAddMovie_empty_string() {
		TodoObject todoObject = new TodoObject();
		todoObject.setTaskName("");
		try {
			restTemplate.postForObject(baseUrl+"/addTask", todoObject, String.class);
		}catch(HttpClientErrorException exception) {
			assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
			assertThat(exception.getResponseBodyAsString()).hasToString(TodoConstants.TASK_IS_MISSING_IN_REQUEST);
		}
	}
	
	@Test
	public void testAddMovie_null() {
		TodoObject todoObject = new TodoObject();
		todoObject.setTaskName(null);
		try {
			restTemplate.postForObject(baseUrl+"/addTask", todoObject, String.class);
		}catch(HttpClientErrorException exception) {
			assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
			assertThat(exception.getResponseBodyAsString()).hasToString(TodoConstants.TASK_IS_MISSING_IN_REQUEST);
		}
	}
	
	@Test
	public void testUpdateTask() {
		String updateTaskName = "Work";
		TodoObject todoObject = new TodoObject();
		todoObject.setId(2);
		todoObject.setTaskName(updateTaskName);
		String status = restTemplate.postForObject(baseUrl+"/updateTask", todoObject, String.class);
		assertThat(status).hasToString(TodoConstants.TASK_UPDATED_SUCCESSFULLY);
		ResponseEntity<TodoObject[]> responseEntity = restTemplate.getForEntity(baseUrl+"/fetchAllTask", TodoObject[].class);
		TodoObject[] testTodoList = responseEntity.getBody();
		String updatedTaskName = testTodoList[1].getTaskName();
		assertThat(updatedTaskName).hasToString(updateTaskName);
	}
	
	@Test
	public void testUpdateTask_Empty_String() {
		String updateTaskName = "";
		TodoObject todoObject = new TodoObject();
		todoObject.setId(2);
		todoObject.setTaskName(updateTaskName);
		try {
			restTemplate.postForObject(baseUrl+"/updateTask", todoObject, String.class);
		}catch(HttpClientErrorException exception) {
			assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
			assertThat(exception.getResponseBodyAsString()).hasToString(TodoConstants.TASK_IS_MISSING_IN_REQUEST);
		}
	}
	
	@Test
	public void testUpdateTask_null() {
		String updateTaskName = null;
		TodoObject todoObject = new TodoObject();
		todoObject.setId(2);
		todoObject.setTaskName(updateTaskName);
		try {
			restTemplate.postForObject(baseUrl+"/updateTask", todoObject, String.class);
		}catch(HttpClientErrorException exception) {
			assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
			assertThat(exception.getResponseBodyAsString()).hasToString(TodoConstants.TASK_IS_MISSING_IN_REQUEST);
		}
	}

	@Test
	public void testDeleteTask() {
		TodoObject todoObject = new TodoObject();
		todoObject.setId(2);
		todoObject.setTaskName("abs");
		String status = restTemplate.postForObject(baseUrl+"/deleteTask", todoObject, String.class);
		assertThat(status).hasToString(TodoConstants.TASK_DELETED_SUCCESSFULLY);
	}
	
	@Test
	public void testDeleteTask_Task_missing() {
		TodoObject todoObject = new TodoObject();
		todoObject.setId(7);
		todoObject.setTaskName("abs");
		String status = restTemplate.postForObject(baseUrl+"/deleteTask", todoObject, String.class);
		assertThat(status).hasToString(TodoConstants.TASK_IS_MISSING);
	}
	
	@Test
	public void testFetchAllMovies() {
		List<TodoObject> testTodoList = restTemplate.getForObject(baseUrl+"/fetchAllTask", List.class);
		assertTrue(!testTodoList.isEmpty());
		assertThat(testTodoList.size()).isEqualTo(4);
	}
}

