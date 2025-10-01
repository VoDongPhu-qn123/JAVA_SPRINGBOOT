package com.example.identity_service.controller;

import com.example.identity_service.dto.request.ApiResponse;
import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.UserUpdateRequest;
import com.example.identity_service.dto.response.UserResponse;
import com.example.identity_service.entity.User;
import com.example.identity_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;
    @PostMapping
    ApiResponse<UserResponse> createUser(@Valid @RequestBody UserCreationRequest request) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser((request)));
        apiResponse.setCode(200);
        return apiResponse;
    }
    @GetMapping
    List<User> getUsers() {
        return  userService.getUsers();
    }
    @GetMapping("/{userId}")
    UserResponse getUser(@PathVariable("userId") String userId) {
        return  userService.getUser(userId);
    }
    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser( @PathVariable String userId,@Valid @RequestBody UserUpdateRequest request) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.updateUser(userId,request));
        apiResponse.setCode((200));
        return apiResponse;
    }
    @PatchMapping("/{userId}")
    User updatePatchUser( @PathVariable String userId,@Valid @RequestBody UserUpdateRequest request) {
        return userService.updatePathchUser(userId,request);
    }
    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId) {
         userService.deleteUser(userId);
         return "User has been deleted";
    }
}
