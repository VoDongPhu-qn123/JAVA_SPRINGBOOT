package com.example.identity_service.service;

import com.example.identity_service.Exception.AppException;
import com.example.identity_service.Exception.ErrorCode;
import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.UserUpdateRequest;
import com.example.identity_service.dto.response.UserResponse;
import com.example.identity_service.entity.Role;
import com.example.identity_service.entity.User;
import com.example.identity_service.repository.RoleRepository;
import com.example.identity_service.repository.UserRepository;
import com.example.identity_service.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor // sẽ generate constructor có tham số cho những field final
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {

   UserRepository userRepository;
   RoleRepository roleRepository;
   UserMapper userMapper;
   PasswordEncoder passwordEncoder;
    public UserResponse createUser(UserCreationRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request); //(map) dữ liệu từ DTO request sang Entity User.
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<Role> roles = new HashSet<>();  //Tạo một tập hợp rỗng (HashSet) chứa các phần tử kiểu Role entity, đặt tên là roles.
        roles.add(roleRepository.findById("USER").get()); // Thêm bản ghi có name là USER vào roles
        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }
    //@PreAuthorize("hasAuthority('create_DATA')")
    @PreAuthorize("hasRole('ADMIN')") // Kiểm tra quyền truy cập trước khi chạy method này
    public List<User> getUsers() {
        log.info("In method get Users");
        return userRepository.findAll();
    }
    @PostAuthorize("returnObject.username == authentication.name") // Kiểm tra quyền truy cập sau khi  chạy method này, nếu thỏa mãn thì return về giá trị, ko thì báo lỗi
    public UserResponse getUser(String id) {
        log.info("In method get User by Id");
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND))) ;
    }
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(()-> new AppException((ErrorCode.USER_NOT_FOUND)));
        return userMapper.toUserResponse((user));
    }
    public  UserResponse updateUser(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return  userMapper.toUserResponse(userRepository.save(user));
    }
    public  User updatePathchUser(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
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
        User user = userRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
        userRepository.deleteById(id);
    }
}
