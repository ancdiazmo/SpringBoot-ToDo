package com.apress.todo.domain;

public class ToDoBuilder {
	
	private static ToDoBuilder instance = new ToDoBuilder ();
	private String id = null; //cuando se hace la llamada encadenada de ToDoBuilder.withId(myId).build se agrega el atributo
	private String description = ""; //cuando se hace la llamada encadenada de ToDoBuilder.withDescription(myDescription).build se agrega el atributo
	
	
	private ToDoBuilder () {}
	
	public static ToDoBuilder create () {
		return instance;
	}
	
	public ToDoBuilder withDescription (String description) {
		this.description = description;
		return instance;
	}
	
	public ToDoBuilder withId (String id) {
		this.id = id;
		return instance;
	}
	
//	public ToDo build () {
//		ToDo result = new ToDo (this.description);
//		if(id != null) {
//			result.setId(id);
//		}
//		return result;
//	}
	
}
