package com.example.task_reminder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.task_reminder.model.Task;
import com.example.task_reminder.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;


    public Task addTask(Task task) {
        try {
            taskRepository.addTask(task);
            return task;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add task", e);
        }
    }


    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    public List<Task> getCompletedTasks() {
        return taskRepository.getCompletedTasks();
    }
    public void deleteTask(int id) {
        boolean deleted = taskRepository.deleteTask(id);
        if (!deleted) {
            throw new RuntimeException("Task not found with id: " + id);
        }
    }

    public List<Task> getPendingTasks() {
        return taskRepository.getPendingTasks();
    }

    public Task getTaskById(int id) {
        Task task = taskRepository.getTaskById(id);
        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with id: " + id);
        }
        return task;
    }


    public void markCompleted(int id) {
        boolean success = taskRepository.markCompleted(id);
        if (!success) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to mark task as completed for id: " + id);
        }
    }


    public int countAll() { return taskRepository.countAll(); }
    public int countCompleted() { return taskRepository.countCompleted(); }
    public int countPending() { return taskRepository.countPending(); }
}