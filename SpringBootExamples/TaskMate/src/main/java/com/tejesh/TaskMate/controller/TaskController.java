package com.tejesh.TaskMate.controller;


import com.tejesh.TaskMate.payload.TaskDto;
import com.tejesh.TaskMate.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("taskmate")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("{userId}/tasks")
    public ResponseEntity<TaskDto> saveTask( @PathVariable(name="userId") long userId,
                                             @RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(taskService.saveTask(userId,taskDto), HttpStatus.CREATED);


    }
    @GetMapping("{userId}/tasks")
    public ResponseEntity<List<TaskDto>> getAllTasks(@PathVariable(name="userId") long userId) {

        return new ResponseEntity<>(taskService.getAllTasks(userId),HttpStatus.OK);
    }

    @GetMapping("{userId}/tasks/{taskId}")
    public ResponseEntity<TaskDto> getTaskByUsersId(@PathVariable(name="userId") long userId,
                                                    @PathVariable(name = "taskId") long taskId) {

        return new ResponseEntity<>(taskService.getTaskByUsersId(userId,taskId),HttpStatus.OK);
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable(name = "taskId") long taskId,
                                              @RequestBody TaskDto taskDto) {

        return new ResponseEntity<>(taskService.updateTask(taskId,taskDto),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{userId}/tasks/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable(name="userId") long userId,
                                                    @PathVariable(name = "taskId") long taskId) {

        return new ResponseEntity<>("Task Deleted Successfully",HttpStatus.OK);
    }


}
