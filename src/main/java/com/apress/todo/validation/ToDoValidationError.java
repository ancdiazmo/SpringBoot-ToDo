package com.apress.todo.validation;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ToDoValidationError {
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<String> errors = new ArrayList<String> ();
	
	private final String errorMessage;
	
	public ToDoValidationError (String error) {
		this.errorMessage = error;
	}
	
	public void addValidationError(String error) {
		errors.add(error);
	}
	
	public List<String> getErrors () {
		return this.errors;
	}
	
	public String getErrorMessage () {
		return errorMessage;
	}
	
}
