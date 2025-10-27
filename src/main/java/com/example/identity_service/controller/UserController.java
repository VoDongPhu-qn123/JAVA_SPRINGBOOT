package com.example.identity_service.controller;

import com.example.identity_service.dto.response.ApiResponse;
import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.UserUpdateRequest;
import com.example.identity_service.dto.response.UserResponse;
import com.example.identity_service.entity.User;
import com.example.identity_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
        var authentication = SecurityContextHolder.getContext().getAuthentication(); // Lấy thông tin người dùng đang đăng nhập (SecurityContextHolder là nơi Spring Security lưu thông tin người dùng đang đăng nhập, getContext() → lấy ra ngữ cảnh bảo mật hiện tại, getAuthentication() → lấy ra đối tượng Authentication)
        authentication.getAuthorities().forEach(
                grantedAuthority -> log.info("Role: {}", grantedAuthority.getAuthority()) // grantedAuthority là một quyền trong danh sách quyền
        );
        return  userService.getUsers();
    }
    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder().result(userService.getMyInfo()).build();
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
