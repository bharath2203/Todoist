package com.bgr.sqlsampleapplication.controller;

import com.bgr.sqlsampleapplication.entity.Todo;
import com.bgr.sqlsampleapplication.repository.TodoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Todo todo) {
        Todo savedTodo = todoRepository.save(todo);
        return ResponseEntity.ok(savedTodo.getId());
    }

    @GetMapping
    public ResponseEntity<Iterable<Todo>> getAllTodos() {
        Iterable<Todo> allTodos = todoRepository.findAll();
        return ResponseEntity.ok(allTodos);
    }
}
