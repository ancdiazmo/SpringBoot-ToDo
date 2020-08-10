package com.apress.todo.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.apress.todo.domain.ToDo;
import com.apress.todo.domain.ToDoBuilder;
import com.apress.todo.repository.CommonRepository;
import com.apress.todo.validation.ToDoValidationError;
import com.apress.todo.validation.ToDoValidationErrorBuilder;

@RestController
@RequestMapping("/api")
public class ToDoController {
	
	private CommonRepository<ToDo> repository;
	
	@Autowired
	public ToDoController (CommonRepository<ToDo> repository) { 
		this.repository = repository;
	}
	
	@GetMapping("/todo")
	public ResponseEntity<Iterable<ToDo>> getToDos() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/todo/{id}")
	public ResponseEntity<ToDo> getToDoById (@PathVariable String id) {
		return ResponseEntity.ok(repository.findById(id));
	}
	
	@PatchMapping("/todo/{id}") //PatchMapping se usa de forma indistinta de RequestMapping
	public ResponseEntity<ToDo> setCompleted (@PathVariable String id) {
		
		ToDo result = repository.findById(id);
		result.setCompleted(true);
		repository.save(result);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(result.getId()).toUri();
		
		return ResponseEntity.ok().header("Location", location.toString()).build();
	}
	
	//El metodo createToDo deberia ser llamado por POST para ingresar nuevos ToDos y por PUT para modificar uno ya existente
	//por su lado el metodo save es el que decide si se debe ingresar una nueva instancia o si por el contrario se modifica
	//una que ya existe.
	@RequestMapping(value = "/todo", method = {RequestMethod.POST, RequestMethod.PUT})
	public ResponseEntity<?> createToDo (@Valid @RequestBody ToDo toDo, Errors errors) {
		
//		if(errors.hasErrors()) {
//			return ResponseEntity.badRequest().body(ToDoValidationErrorBuilder.fromBindingError(errors));
//		}
		
		ToDo result = repository.save(toDo);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri();

		return ResponseEntity.created(location).build();
	}
	
	
	@DeleteMapping("/todo")
	public ResponseEntity<ToDo> deleteToDo(@RequestBody ToDo toDo){
		repository.delete(toDo);
		return ResponseEntity.noContent().build();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ToDoValidationError handleException (Exception exception) {
		return new ToDoValidationError(exception.getMessage());
	}
	
	
	
	
}
