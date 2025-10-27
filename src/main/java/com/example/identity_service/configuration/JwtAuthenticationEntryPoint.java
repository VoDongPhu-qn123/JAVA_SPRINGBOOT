package com.example.identity_service.configuration;

import com.example.identity_service.Exception.ErrorCode;
import com.example.identity_service.dto.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;
        response.setStatus(errorCode.getStatusCode().value()); //.value() dùng để lấy số mã trạng thái HTTP (int) từ enum HttpStatus
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // phần nội dung trả về là json
        ApiResponse<?> apiResponse = ApiResponse.builder().code(errorCode.getCode()).message(errorCode.getMessage()).build();
        ObjectMapper objectMapper = new ObjectMapper(); // dùng để chuyển một object Java -> chuỗi Json
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse)); //ghi thẳng vào phản hồi HTTP để gửi về client.
        response.flushBuffer(); //buộc server gửi toàn bộ dữ liệu response còn trong bộ nhớ đệm về client ngay lập tức.
    }
}
