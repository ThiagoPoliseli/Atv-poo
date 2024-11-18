package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Criar uma nova tarefa
    public MyTask createTask(MyTask myTask) {
        myTask.setCreationDate(LocalDate.now());
        myTask.setStatus("A Fazer");
        return taskRepository.save(myTask);
    }

    // Listar todas as tarefas organizadas por coluna
    public List<Task> getTasksByStatus(String status) {
        return taskRepository.findByStatusOrderByPriority(status);
    }

    // Mover a tarefa para a próxima coluna
    public MyTask moveTask(Long id) {
        MyTask myTask = taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Tarefa não encontrada com ID: " + id));
        if ("A Fazer".equals(myTask.getStatus())) {
            myTask.setStatus("Em Progresso");
        } else if ("Em Progresso".equals(myTask.getStatus())) {
            myTask.setStatus("Concluído");
        }

        return taskRepository.save(myTask);
    }

    // Atualizar uma tarefa
    public MyTask updateTask(Long id, MyTask myTaskDetails) {
        MyTask myTask = taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Tarefa não encontrada com ID: " + id));
        myTask.setTitle(myTaskDetails.getTitle());
        myTask.setDescription(myTaskDetails.getDescription());
        myTask.setPriority(myTaskDetails.getPriority());
        myTask.setDueDate(myTaskDetails.getDueDate());
        return taskRepository.save(myTask);
    }

    // Excluir uma tarefa
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new NoSuchElementException("Tarefa não encontrada com ID: " + id);
        }
        taskRepository.deleteById(id);
    }

    // Gerar relatório de tarefas atrasadas
    public List<MyTask> getOverdueTasks() {
        List<Task> list = taskRepository.findAll().stream()
                .filter(task -> task.getDueDate() != null && task.getDueDate().isBefore(LocalDate.now()) && !"Concluído".equals(task.getStatus()))
                .toList();
        return list;
    }
}
