package com.apress.todo.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository; 
import com.apress.todo.domain.ToDo;

@Repository
public class ToDoRepository implements CommonRepository<ToDo>{
	
	private Map<String, ToDo> toDos = new HashMap<String, ToDo>();
	
	private Comparator<Map.Entry<String,ToDo>> entryComparator = (Map.
			Entry<String, ToDo> o1, Map.Entry<String, ToDo> o2) -> {
			return o1.getValue().getCreated().compareTo(o2.getValue().
			getCreated());
	};

	@Override
	public ToDo save(ToDo domain) {
		ToDo isIn = toDos.get(domain.getId());
		if (isIn != null) {
			domain.setModified(LocalDateTime.now());
		}
		toDos.put(domain.getId(), domain);
		return toDos.get(domain.getId());
	}	

	@Override
	public Iterable<ToDo> save(Collection<ToDo> domain) {
		domain.forEach(n -> save(n));
		return findAll();
	}
	
	@Override
	public void delete(ToDo domain) {
		toDos.remove(domain.getId());
	}
	
	@Override
	public ToDo findById(String id) {
		return toDos.get(id);
	}

	@Override
	public Iterable<ToDo> findAll() {
		
		return toDos.entrySet().stream().sorted(entryComparator).
				map(Map.Entry::getValue).collect(Collectors.toList());
	}
	

	
}
