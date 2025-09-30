package com.example.identity_service.controller;

import com.example.identity_service.dto.request.ApiResponse;
import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.UserUpdateRequest;
import com.example.identity_service.entity.User;
import com.example.identity_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping
    ApiResponse<User> createUser(@Valid @RequestBody UserCreationRequest request) {
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser((request)));
        apiResponse.setCode(200);
        return apiResponse;
    }
    @GetMapping
    List<User> getUsers() {
        return  userService.getUsers();
    }
    @GetMapping("/{userId}")
    User getUser(@PathVariable("userId") String userId) {
        return  userService.getUser(userId);
    }
    @PutMapping("/{userId}")
    User updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId,request);
    }
    @PatchMapping("/{userId}")
    User updatePatchUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return userService.updatePathchUser(userId,request);
    }
    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId) {
         userService.deleteUser(userId);
         return "User has been deleted";
    }
}
