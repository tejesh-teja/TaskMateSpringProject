package com.tejesh.TaskMate.serivceImpl;

import com.tejesh.TaskMate.entity.Task;
import com.tejesh.TaskMate.entity.Users;
import com.tejesh.TaskMate.excpetionHandling.APIException;
import com.tejesh.TaskMate.excpetionHandling.TaskNotFound;
import com.tejesh.TaskMate.excpetionHandling.UserNotFound;
import com.tejesh.TaskMate.payload.TaskDto;
import com.tejesh.TaskMate.repository.TaskRepository;
import com.tejesh.TaskMate.repository.UserRepository;
import com.tejesh.TaskMate.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl  implements TaskService {

   @Autowired
   private ModelMapper modelMapper;
   @Autowired
   private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public TaskDto saveTask(long userId, TaskDto taskDto) {

        Optional<Users> optionalUsers = userRepository.findById(userId);
        Users users;
        if(optionalUsers.isPresent()) {
            users=optionalUsers.get();
        } else {
            throw new UserNotFound("User id "+userId+" Not Found");
        }


        Task task = modelMapper.map(taskDto,Task.class);

        task.setUsers(users);
        // storing users into db

        Task savedTask = taskRepository.save(task);

        return modelMapper.map(savedTask,TaskDto.class);

    }

    @Override
    public List<TaskDto> getAllTasks(long userId) {
        userRepository.findById(userId).orElseThrow(
                () -> new UserNotFound("User id "+userId+" Not Found"));

        List<Task> tasks = taskRepository.findAllByUsersId(userId);

        return tasks.stream()
                .map(task -> modelMapper.map(task,TaskDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto getTaskByUsersId(long userId, long taskId) {
        Users users = userRepository.findById(userId).orElseThrow(
                                    () -> new UserNotFound("User id "+userId+" Not Found"));

        Task task =  taskRepository.findById(taskId).orElseThrow(
                                    () -> new TaskNotFound("Task id "+taskId+" Not Found"));

        if(users.getId() != task.getUsers().getId()) {
            throw new APIException("Task id "+taskId+" not belongs to user id "+userId);
        }
        return modelMapper.map(task,TaskDto.class);
    }

    @Override
    public void deleteTask(long userId, long taskId) {
        Users users = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFound("User id "+userId+" Not Found"));

        Task task =  taskRepository.findById(taskId).orElseThrow(
                () -> new TaskNotFound("Task id "+taskId+" Not Found"));

        if(users.getId() != task.getUsers().getId()) {
            throw new APIException("Task id "+taskId+" not belongs to user id "+userId);
        }
       taskRepository.deleteById(taskId);
    }

    @Override
    public TaskDto updateTask(long taskId, TaskDto taskDto) {

        Task task =  taskRepository.findById(taskId).orElseThrow(
                () -> new TaskNotFound("Task id "+taskId+" Not Found"));


        task.setTaskName(taskDto.getTaskName());
        Task updatedTask = taskRepository.save(task);

        return modelMapper.map(updatedTask,TaskDto.class);

    }
}
