package com.tejesh.TaskMate.security;

import com.tejesh.TaskMate.entity.Users;
import com.tejesh.TaskMate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User email "+email+" not found"));

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_ADMIN");


        return new User(user.getEmail(),user.getPassword(),userAuthority(roles));
    }

    private Collection<? extends GrantedAuthority> userAuthority(Set<String> roles) {

        return roles.stream().map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());

    }
}
