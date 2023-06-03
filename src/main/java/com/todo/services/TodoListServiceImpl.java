package com.todo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.todo.constants.TodoConstants;
import com.todo.model.TodoObject;

@Service
public class TodoListServiceImpl implements TodoListService{
	//mocking db
	private List<TodoObject> todoList = new ArrayList<>();
	private int lastID = 0;
	
	public TodoListServiceImpl() {
		todoList.add(createTask("Eat"));
		todoList.add(createTask("Code"));
		todoList.add(createTask("Sleep"));
		todoList.add(createTask("Repeat"));
	}
	
	/**
	 * This method fetchs tasks from the db
	 * @author Kunal Haragaball
	 * @return List<TodoObject>
	 */
	public List<TodoObject> getTodoList() {
		return todoList;
	}
	
	/**
	 * This method adds task in the db
	 * @author Kunal Haragaball
	 * @param String taskName
	 * @return String status
	 */
	public String addTaskToList(String taskName) {
		if(todoList == null) {
			todoList = new ArrayList<>();
		}
		todoList.add(createTask(taskName));
		return TodoConstants.TASK_ADDED_SUCCESSFULLY;
	}

	/**
	 * This method create task returns the task
	 * @author Kunal Haragaball
	 * @param String taskName
	 * @return TodoObject
	 */
	private TodoObject createTask(String taskName) {
		TodoObject todoObject = new TodoObject(++lastID, taskName, new Date());
		return todoObject;
	}
	
	/**
	 * This method update task, returns the status
	 * @author Kunal Haragaball
	 * @param TodoObject
	 * @return String
	 */
	public String updateTask(TodoObject updateTask) {
		String updateStatus = TodoConstants.TASK_IS_MISSING;
		int index =0;
		for(TodoObject todo : todoList) {
			if(todo.getId()  == updateTask.getId()) {
				updateStatus = TodoConstants.TASK_UPDATED_SUCCESSFULLY;
				todoList.set(index, updateTask);
			}
			index++;
		}
		return updateStatus;
	}
	
	/**
	 * This method deletes task returns the status
	 * @author Kunal Haragaball
	 * @param TodoObject
	 * @return String
	 */
	public String deleteTask(TodoObject deleteTask) {
		String updateStatus = TodoConstants.TASK_IS_MISSING;
		List<TodoObject> updatedList = todoList.stream().filter(todoObj -> todoObj.getId() != deleteTask.getId()).collect(Collectors.toList());
		if(updatedList.size()<todoList.size()) {
			updateStatus = TodoConstants.TASK_DELETED_SUCCESSFULLY;
			todoList = updatedList;
		}
		return updateStatus;
	}
}
