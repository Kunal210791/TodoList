package com.todo.model;

import java.util.Date;

import com.todo.constants.TodoConstants;

public class TodoObject {
	private int id;
	private String taskName;
	private Date taskDate;
	private String taskStatus;
	private Date taskCompletionDate;
	
	public TodoObject() {
		// TODO Auto-generated constructor stub
	}
	
	public TodoObject(int id, String taskName,	Date taskDate) {
		this.id=id;
		this.taskName=taskName;
		this.taskDate=taskDate;
		this.taskStatus = TodoConstants.NEW;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Date getTaskDate() {
		return taskDate;
	}
	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public Date getTaskCompletionDate() {
		return taskCompletionDate;
	}
	public void setTaskCompletionDate(Date taskCompletionDate) {
		this.taskCompletionDate = taskCompletionDate;
	}
	@Override
	public String toString() {
		return "TodoObject [id=" + id + ", taskName=" + taskName + ", taskDate=" + taskDate + ", taskStatus="
				+ taskStatus + ", taskCompletionDate=" + taskCompletionDate + "]";
	}
}
