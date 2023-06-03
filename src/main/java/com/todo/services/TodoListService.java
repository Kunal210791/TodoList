package com.todo.services;

import java.util.List;
import com.todo.model.TodoObject;

public interface TodoListService {
	
	public List<TodoObject> getTodoList() ;
	
	public String addTaskToList(String taskName);
	
	public String updateTask(TodoObject updateTask);
	
	public String deleteTask(TodoObject deleteTask);

}
