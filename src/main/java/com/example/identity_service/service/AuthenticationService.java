package com.example.identity_service.service;

import com.example.identity_service.Exception.AppException;
import com.example.identity_service.Exception.ErrorCode;
import com.example.identity_service.dto.request.AuthenticationRequest;
import com.example.identity_service.dto.request.IntrospectRequest;
import com.example.identity_service.dto.response.AuthenticationResponse;
import com.example.identity_service.dto.response.IntrospectResponse;
import com.example.identity_service.entity.User;
import com.example.identity_service.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    @NonFinal
    @Value("${jwt.signerKey}") // đọc một biến từ file yamal
     protected String SIGNER_KEY;
    public AuthenticationResponse Authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!authenticated) {
            throw  new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(request.getUsername());
        return AuthenticationResponse.builder()
                .isAuthenticated(true)
                .token(token)
                .build();
    }
    public IntrospectResponse Introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        JWSVerifier jwsVerifier = new MACVerifier((SIGNER_KEY.getBytes())); //Tạo ra một đối tượng kiểm tra chữ ký JWT (verifier)
        SignedJWT signedJWT = SignedJWT.parse(token); // Nó giải mã token JWT dạng chuỗi -> thành đối tượng SignedJWT có thể truy cập từng phần (header, payload, signature)
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime(); // Lấy thời gian hết hạn của token
        var verified = signedJWT.verify(jwsVerifier);
        return  IntrospectResponse.builder()
                .valid(verified && expirationTime.after(new Date()))
                .build();
    }
    private String generateToken(String username) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512); // Tạo header trong JWT
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username) // Chủ sở hữu token
                .issuer("devteria.com") // Người phát hành token
                .issueTime(new Date()) // Thời gian phát hành token
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli())) //Thời gian hết hạn của token
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());// Tạo payload trong JWT

        JWSObject jwsObject = new JWSObject(header, payload); // Tạo ra một JWT chưa được ký
        try {
            jwsObject.sign(new MACSigner((SIGNER_KEY.getBytes()))); // Tạo chữ ký cho JWT
            return  jwsObject.serialize(); // chuyển JWT về định dạng chuẩn
        } catch (JOSEException e) {
            log.error("Cannot create token" ,e);
            throw new RuntimeException(e);
        }
    }

}
