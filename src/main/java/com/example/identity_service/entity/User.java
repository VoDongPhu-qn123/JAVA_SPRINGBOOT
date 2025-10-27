package com.example.identity_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private  String username;
    private  String password;
    private  String firstName;
    private  String lastName;
    private LocalDate dayOfBirth;

    @ManyToMany
    Set<Role> roles; // Là danh sách không trùng quyền của user
}
