package com.quantafic.JWTSecurity.Controller.UserController;

import com.quantafic.JWTSecurity.ApiResponse.ApiResponse;
import com.quantafic.JWTSecurity.DTO.UserLoginDTO;
import com.quantafic.JWTSecurity.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/")
    public String greetUser() {
        return "Welcome to user page";
    }

    // User (staff) login via Employee id and password
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody UserLoginDTO user) {
        String token = service.verify(user);
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "Login successful", token));
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<?>> getProfile() {
        Object response = service.getUser();
        return ResponseEntity.ok(new ApiResponse<>(200, true, null, "User found", response));
    }
}
