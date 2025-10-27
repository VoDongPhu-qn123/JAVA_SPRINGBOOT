package com.example.identity_service.Exception;

import com.example.identity_service.dto.response.ApiResponse;
import com.example.identity_service.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
   @ExceptionHandler(value= Exception.class)
    ResponseEntity<ApiResponse<User>> handlingException(RuntimeException exception){
       ApiResponse<User> apiResponse = new ApiResponse<>();
       apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
       apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
       return ResponseEntity.badRequest().body(apiResponse);
   }
    @ExceptionHandler(value= AppException.class)
    ResponseEntity<ApiResponse<User>> handlingAppException(AppException exception){
      ErrorCode errorCode = exception.getErrorCode();
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }
    @ExceptionHandler(value = org.springframework.security.access.AccessDeniedException.class)
    ResponseEntity<ApiResponse<User>> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getStatusCode()).body(ApiResponse.<User>builder().code(errorCode.getCode()).message(errorCode.getMessage()).build());
    }

   @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<User>> handlingValidation(MethodArgumentNotValidException exception){
       //List<String> filedErrors = exception.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).toList(); // List<FieldError> -> Stream<FieldError> -> Stream<String> -> List<String>
       //String message = filedError!=null ? filedError.getDefaultMessage(): "Validation Error";
       var fieldError = exception.getFieldError();
       String enumKey = fieldError!=null ? fieldError.getDefaultMessage(): "Validation Error";
       ErrorCode errorCode = ErrorCode.INVALID_KEY;
       try {
            errorCode = ErrorCode.valueOf(enumKey); // tìm trong enum có hằng số có tên = giá trị của biến enumKey -> nếu có trả về ErrorCode.USERNAME_INVALID(ví dụ) or nếu ko thì lỗi IllegalArgumentException.
       }catch (IllegalArgumentException e) {
           System.out.println("⚠️ Invalid enum key from validation message: " + enumKey);
       }

       ApiResponse<User> apiResponse = new ApiResponse<>();
       apiResponse.setCode(errorCode.getCode());
       apiResponse.setMessage(errorCode.getMessage());
       return  ResponseEntity.badRequest().body(apiResponse);
   }

}
