package com.tejesh.TaskMate.service;

import com.tejesh.TaskMate.payload.TaskDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

    public TaskDto saveTask(long userId,TaskDto taskDto);

    public List<TaskDto> getAllTasks(long userId);

    public TaskDto getTaskByUsersId(long userId, long taskId);

    public void deleteTask(long userId, long taskId);

    public  TaskDto updateTask(long taskId, TaskDto taskDto);
}
