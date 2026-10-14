package com.example.project3.Controller;

import com.example.project3.Api.ApiResponse;
import com.example.project3.DTO.CustomerRegisterDTO;
import com.example.project3.DTO.CustomerUpdateDTO;
import com.example.project3.Model.User;
import com.example.project3.Service.AuthService;
import com.example.project3.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/customer")
@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid CustomerRegisterDTO dto) {
        authService.registerCustomer(dto);
        return ResponseEntity.status(200).body(new ApiResponse("customer registered"));
    }
    // admins and employees
    @GetMapping("/getAllCustomers")
    public ResponseEntity<?> getAllCustomers() {
        return ResponseEntity.status(200).body(customerService.getAllCustomers());
    }
    @GetMapping("/getMyProfile")
    public ResponseEntity<?> getMyProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(customerService.getMyProfile(user));
    }

    @DeleteMapping("/deleteCustomer/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.status(200).body(new ApiResponse("customer deleted"));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user) {
        customerService.deleteMyAccount(user);
        return ResponseEntity.status(200).body(new ApiResponse("customer deleted"));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@AuthenticationPrincipal User user, @RequestBody @Valid CustomerUpdateDTO dto) {
        customerService.updateProfile(user, dto);
        return ResponseEntity.status(200).body(new ApiResponse("customer updated"));
    }

}
