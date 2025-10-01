package com.example.identity_service.dto.request;

import com.example.identity_service.Exception.ErrorCode;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

/*@Getter
@Setter*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min=5, message = "INVALID_USERNAME")
      String username;

    @Size(min=8, message = "INVALID_PASSWORD")
      String password;

      String firstName;
      String lastName;
      LocalDate dayOfBirth;


}
