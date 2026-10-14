package com.example.project3.Controller;

import com.example.project3.Api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {
    @PostMapping("/login")
    public ResponseEntity<?> login() {
        return ResponseEntity.status(200).body(new ApiResponse("login successful"));
    }

    // logout is handled by the logout filter in Configuration
}
