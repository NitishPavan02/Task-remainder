package com.example.task_reminder.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.example.task_reminder.model.Task;

@Service
public class SchedulerService {


    private final ScheduledExecutorService executor =
            Executors.newSingleThreadScheduledExecutor();

    private final ConcurrentHashMap<Integer, ScheduledFuture<?>> scheduledTasks =
            new ConcurrentHashMap<>();

    public boolean scheduleTask(Task task, Runnable reminderLogic) {

        if (task.getDueDate() == null) {
            System.out.println("❌ DUE DATE IS NULL");
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        long delay = Duration.between(now, task.getDueDate()).toMillis();

        if (delay <= 0) {
            System.out.println("❌ DUE DATE ALREADY PASSED");
            return false;
        }

        if (scheduledTasks.containsKey(task.getId())) {
            System.out.println("⚠ TASK ALREADY SCHEDULED");
            return false;
        }

        ScheduledFuture<?> future = executor.schedule(() -> {
            System.out.println("⏰ SCHEDULER TRIGGERED FOR TASK: " + task.getTitle());
            reminderLogic.run();
            scheduledTasks.remove(task.getId());
        }, delay, TimeUnit.MILLISECONDS);

        scheduledTasks.put(task.getId(), future);

        System.out.println("✅ TASK SCHEDULED: " + task.getTitle());
        return true;
    }

    public boolean isTaskScheduled(int taskId) {
        return scheduledTasks.containsKey(taskId);
    }
}