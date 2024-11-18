package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // POST /tasks: Criar uma nova tarefa na coluna "A Fazer"
    @PostMapping
    public ResponseEntity<MyTask> createTask(@RequestBody MyTask myTask) {
        MyTask createdTask = taskService.createTask(myTask);
        return ResponseEntity.ok(createdTask);
    }

    // GET /tasks: Listar todas as tarefas organizadas por coluna
    @GetMapping
    public ResponseEntity<List<MyTask>> getTasksByStatus(@RequestParam String status) {
        List<MyTask> tasks = taskService.getTasksByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    // PUT /tasks/{id}/move: Mover uma tarefa para a próxima coluna
    @PutMapping("/{id}/move")
    public ResponseEntity<MyTask> moveTask(@PathVariable Long id) {
        MyTask updatedTask = taskService.moveTask(id);
        return ResponseEntity.ok(updatedTask);
    }

    // PUT /tasks/{id}: Atualizar uma tarefa (título, descrição, prioridade)
    @PutMapping("/{id}")
    public ResponseEntity<MyTask> updateTask(@PathVariable Long id, @RequestBody MyTask myTaskDetails) {
        MyTask updatedTask = taskService.updateTask(id, myTaskDetails);
        return ResponseEntity.ok(updatedTask);
    }

    // DELETE /tasks/{id}: Excluir uma tarefa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
