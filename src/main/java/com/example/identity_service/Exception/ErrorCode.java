package com.example.identity_service.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Getter

public enum ErrorCode {
    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST), // lỗi 400
    INVALID_KEY(1000,"Invalid enumKey", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND (1002,"User not found", HttpStatus.NOT_FOUND),
    UNCATEGORIZED_EXCEPTION(9999,"uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR), // lỗi 500
    INVALID_USERNAME(1003,"Username must be at least 5 characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004,"Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND), // lỗi 404
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED), // lỗi 401
    UNAUTHORIZED(1007,"You do not have permission", HttpStatus.FORBIDDEN) // lỗi 403
    ;
    private final int  code;
    private  final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }


}
