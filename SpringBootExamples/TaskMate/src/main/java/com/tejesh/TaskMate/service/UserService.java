package com.tejesh.TaskMate.service;

import com.tejesh.TaskMate.entity.Users;
import com.tejesh.TaskMate.payload.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {


    public UserDto createUser(UserDto userDto);
}
