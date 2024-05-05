package com.kmou.server.service;

import com.kmou.server.entity.UserEntity;
import com.kmou.server.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity findByName(String name) {
        return userRepository.findByName(name);
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}
