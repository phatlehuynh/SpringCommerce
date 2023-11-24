package com.example.demo.Controller;


import com.example.demo.Repository.UserRepository;
import com.example.demo.Security.JwtService;
import com.example.demo.Service.Implement.AuthenticationService;
import com.example.demo.Service.Implement.UserService;
import com.example.demo.Utilities.AuthenticationRequest;
import com.example.demo.Utilities.AuthenticationResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")

public class AuthenticationCotroller {
    private final PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    UserRepository repository;
    @Autowired
    JwtService jwtService;



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        try {
            AuthenticationResponse response = authenticationService.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            // Xử lý trường hợp xác thực thất bại: Sai tên người dùng hoặc mật khẩu
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Sai tên người dùng hoặc mật khẩu2.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (UsernameNotFoundException e) {
            // Xử lý trường hợp người dùng không tồn tại
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Người dùng không tồn tại.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (IllegalArgumentException e) {
            // Xử lý trường hợp dữ liệu đầu vào không hợp lệ
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            // Xử lý các trường hợp khác
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Lỗi không xác định.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


}

