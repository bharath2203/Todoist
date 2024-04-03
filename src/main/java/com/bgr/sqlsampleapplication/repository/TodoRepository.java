package com.bgr.sqlsampleapplication.repository;

import com.bgr.sqlsampleapplication.entity.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, String> {
}
