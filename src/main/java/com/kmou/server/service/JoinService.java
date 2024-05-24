package com.kmou.server.service;

import com.kmou.server.dto.JoinDTO;
import com.kmou.server.entity.UserEntity;
import com.kmou.server.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDTO) {
        String username = joinDTO.getUsername();
        String name = joinDTO.getName();
        String password = joinDTO.getPassword();
        Long phoneNumber = joinDTO.getPhoneNumber();

        Boolean isExist = userRepository.existsByUsername(username);

        if (isExist) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }

        UserEntity data = new UserEntity();

        data.setUsername(username);
        data.setName(name);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_USER");
        data.setPhoneNumber(phoneNumber);

        userRepository.save(data);
    }

}
