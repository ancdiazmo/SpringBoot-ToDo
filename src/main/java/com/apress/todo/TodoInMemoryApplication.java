package com.apress.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.apress.todo.domain.ToDoBuilder;

@SpringBootApplication
public class TodoInMemoryApplication {

	public static void main(String[] args) {
//		System.out.println(ToDoBuilder.create().withDescription("Soy una descripcion").build().toString());
		SpringApplication.run(TodoInMemoryApplication.class, args);
	}

}
