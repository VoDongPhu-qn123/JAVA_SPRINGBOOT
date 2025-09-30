package com.example.identity_service.Exception;

public enum ErrorCode {
    USER_EXISTED(1001, "User existed"),
    USER_NOT_FOUND (1002,"User not found"),
    UNCATEGORIZED_EXCEPTION(9999,"uncategorized error");

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
