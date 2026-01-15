package com.example.task_reminder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.task_reminder.model.Task;
import com.example.task_reminder.repository.TaskRepository;

@Service
public class CompletionService {

    @Autowired
    private TaskRepository taskRepository;

    public void markCompleted(int taskId) {
        Task task = taskRepository.getTaskById(taskId);
        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with id: " + taskId);
        }

        boolean success = taskRepository.markCompleted(taskId);
        if (!success) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to mark task as completed for id: " + taskId);
        }
    }


    public Task getTaskById(int taskId) {
        Task task = taskRepository.getTaskById(taskId);
        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with id: " + taskId);
        }
        return task;
    }
}