package com.example.identity_service.service;

import com.example.identity_service.Exception.AppException;
import com.example.identity_service.Exception.ErrorCode;
import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.UserUpdateRequest;
import com.example.identity_service.entity.User;
import com.example.identity_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User createUser(UserCreationRequest request) {
        User user = new User();
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDayOfBirth(request.getDayOfBirth());
        return userRepository.save(user);
    }
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    public User getUser(String id) {
        return  userRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
    }
    public  User updateUser(String id, UserUpdateRequest request) {
        User user = getUser(id);
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDayOfBirth(request.getDayOfBirth());
        return  userRepository.save(user);
    }
    public  User updatePathchUser(String id, UserUpdateRequest request) {
        User user = getUser(id);
        if(request.getPassword() != null) {
            user.setPassword(request.getPassword());
        }
        if(request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if(request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if(request.getDayOfBirth() != null) {
            user.setDayOfBirth(request.getDayOfBirth());
        }

        return  userRepository.save(user);
    }
    public void deleteUser(String id) {
        User user = getUser(id);
        userRepository.deleteById(id);
    }
}
