package com.task.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.task.app.exceptions.ToDoException;
import com.task.app.mapper.TaskInDTOToTask;
import com.task.app.persistence.entity.Task;
import com.task.app.persistence.entity.TaskStatus;
import com.task.app.persistence.repository.TaskRepository;
import com.task.app.service.dto.TaskInDTO;

@Service
public class TaskService {
	
	private final TaskRepository repository;
	private final TaskInDTOToTask mapper;
	
	public TaskService(TaskRepository repository, TaskInDTOToTask mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}
	
	public Task createTask(TaskInDTO taskInDTO) {
		Task task = mapper.map(taskInDTO);
		return this.repository.save(task);
		
	}
	
	public List<Task> findAll(){
		return this.repository.findAll();
	}
	
	public List<Task> findAllByTaskStatus(TaskStatus status){
		return this.repository.findAllByTaskStatus(status);
	}
	
	@Transactional
	public void updateTaskAsFinished(Long id) {
		Optional<Task> optionalTask = this.repository.findById(id);
		
		if (optionalTask.isEmpty()) {
			throw new ToDoException("Task not found", HttpStatus.NOT_FOUND);			
		}
		
		this.repository.markTaskAsFinished(id);
	}
	
	@Transactional
	public void deleteById(Long id) {
		Optional<Task> optionalTask = this.repository.findById(id);
		
		if (optionalTask.isEmpty()) {
			throw new ToDoException("Task not found", HttpStatus.NOT_FOUND);			
		}
		
		this.repository.deleteById(id);
	}
	
}
