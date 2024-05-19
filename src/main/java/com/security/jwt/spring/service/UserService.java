package com.security.jwt.spring.service;

import com.security.jwt.spring.dto.request.UserUpdateReq;
import com.security.jwt.spring.dto.response.UserResponse;
import com.security.jwt.spring.models.entity.User;
import com.security.jwt.spring.models.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private ValidationService validationService;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserResponse update(UserUpdateReq dto) {
        validationService.validation(dto);

        if(!Objects.nonNull(dto.getEmail()) && !Objects.nonNull(dto.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email and username not be null");
        }

        User user = userRepository.findByEmail(dto.getEmail())
                        .orElseThrow(() -> new UsernameNotFoundException("email user not found"));

        user.setId(user.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        userRepository.save(user);

        return userResponse(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(this::userResponse).toList();
    }

    @Transactional(readOnly = true)
    public UserResponse get(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "email not found"));

        return userResponse(user);
    }

    private UserResponse userResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
