package com.example.identity_service.Exception;

public enum ErrorCode {
    USER_EXISTED(1001, "User existed"),
    INVALID_KEY(1000,"Invalid enumKey "),
    USER_NOT_FOUND (1002,"User not found"),
    UNCATEGORIZED_EXCEPTION(9999,"uncategorized error"),
    INVALID_USERNAME(1003,"Username must be at least 5 characters"),
    INVALID_PASSWORD(1004,"Password must be at least 8 characters"),
    USER_NOT_EXISTED(1005, "User not existed"),
    UNAUTHENTICATED(1006, "Unauthenticated")
    ;
    private final int  code;
    private  final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
