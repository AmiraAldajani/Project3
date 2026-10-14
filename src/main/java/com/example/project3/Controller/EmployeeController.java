package com.example.project3.Controller;

import com.example.project3.Api.ApiResponse;
import com.example.project3.DTO.EmployeeRegisterDTO;
import com.example.project3.DTO.EmployeeUpdateDTO;
import com.example.project3.Model.User;
import com.example.project3.Service.AuthService;
import com.example.project3.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/employee")
@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AuthService authService;

    @GetMapping("/getAllEmployees")
    public ResponseEntity<?> getAllEmployees() {
        return ResponseEntity.status(200).body(employeeService.getAllEmployees());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid EmployeeRegisterDTO dto){
        authService.registerEmployee(dto);
        return ResponseEntity.status(200).body(new ApiResponse("Employee registered successfully"));
    }

    @GetMapping("/getMyProfile")
    public ResponseEntity<?> getMyProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(employeeService.getMyProfile(user));
    }

    @DeleteMapping("/deleteEmployee/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Integer employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.status(200).body(new ApiResponse("Employee deleted"));
    }

    @PutMapping("/updateEmployee/{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable Integer employeeId, @RequestBody @Valid EmployeeUpdateDTO dto) {
        employeeService.updateEmployee(employeeId, dto);
        return ResponseEntity.status(200).body(new ApiResponse("Employee updated"));
    }
}
