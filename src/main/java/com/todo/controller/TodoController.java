package com.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.constants.TodoConstants;
import com.todo.model.TodoObject;
import com.todo.services.TodoListService;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/todo")
public class TodoController {
	
	@Autowired
	TodoListService todoListService;
	
	@GetMapping(value = "/fetchAllTask")
	public ResponseEntity<List<TodoObject>> getTodoList(HttpServletRequest request){
		return new ResponseEntity<List<TodoObject>>(todoListService.getTodoList(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/addTask")
	public ResponseEntity<String> addTask(HttpServletRequest request, @RequestBody TodoObject todoObject){
		String status = "";
		ResponseEntity<String> response = null;
		if(StringUtils.isBlank(todoObject.getTaskName())) {
			response = new ResponseEntity<String>(TodoConstants.TASK_IS_MISSING_IN_REQUEST, HttpStatus.BAD_REQUEST);
		}else {
			status = todoListService.addTaskToList(todoObject.getTaskName());
			response =  new ResponseEntity<String>(status, HttpStatus.OK);
		}
		return response;
	}
	
	@PostMapping(value = "/deleteTask")
	public ResponseEntity<String> deleteTask(HttpServletRequest request, @RequestBody TodoObject deleteTaskObject){
		String status = "";
		ResponseEntity<String> response = null;
		status = todoListService.deleteTask(deleteTaskObject);
		response =  new ResponseEntity<String>(status, HttpStatus.OK);
		return response;
	}
	
	@PostMapping(value = "/updateTask")
	public ResponseEntity<String> updateTask(HttpServletRequest request, @RequestBody TodoObject todoObject){
		String status = "";
		ResponseEntity<String> response = null;
		if(StringUtils.isBlank(todoObject.getTaskName())) {
			response = new ResponseEntity<String>(TodoConstants.TASK_IS_MISSING_IN_REQUEST, HttpStatus.BAD_REQUEST);
		}else {
			status = todoListService.updateTask(todoObject);
			response =  new ResponseEntity<String>(status, HttpStatus.OK);
		}
		return response;
	}
}
