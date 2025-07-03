package com.tejesh.TaskMate.serivceImpl;

import com.tejesh.TaskMate.entity.Users;
import com.tejesh.TaskMate.payload.UserDto;
import com.tejesh.TaskMate.repository.UserRepository;
import com.tejesh.TaskMate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Users users = userDtoToEntity(userDto);
        // convert userdto to user class to store the data
        Users savedUsers = userRepository.save(users);
        return entityTOUserDto(savedUsers);

    }

    private Users userDtoToEntity(UserDto userDto) {
        Users users = new Users();
        users.setName(userDto.getName());
        users.setEmail(userDto.getEmail());
        users.setPassword(userDto.getPassword());

        return users;
    }

    private UserDto entityTOUserDto(Users savedUsers) {
        UserDto userDto = new UserDto();

        userDto.setId(savedUsers.getId());
        userDto.setName(savedUsers.getName());
        userDto.setEmail(savedUsers.getEmail());
        userDto.setPassword(savedUsers.getPassword());

        return userDto;


    }
}
