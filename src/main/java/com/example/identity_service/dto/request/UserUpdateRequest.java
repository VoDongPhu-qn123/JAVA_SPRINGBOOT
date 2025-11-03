package com.example.identity_service.dto.request;

import com.example.identity_service.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {

    @Size(min=8, message = "INVALID_PASSWORD")
    String password;


    String firstName;
    String lastName;
    @DobConstraint(min = 20, message = "INVALID_DOB")
    LocalDate dayOfBirth;
    List<String> roles;

}
