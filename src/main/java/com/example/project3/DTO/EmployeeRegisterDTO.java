package com.example.project3.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRegisterDTO {

    @NotEmpty(message = "username cant be null")
    @Size(min = 4, max = 10, message = "username length must be between 4 and 10 characters")
    private String username;

    @NotEmpty(message = "password cant be null")
    @Size(min = 6, message = "password length must be at least 6 characters")
    private String password;

    @NotEmpty(message = "name cant be null")
    @Size(min = 2, max = 20, message = "name length must be between 2 and 20 characters")
    private String name;

    @NotEmpty(message = "email cant be null")
    @Email(message = "email must be a valid email format")
    private String email;

    @NotEmpty(message = "position cant be null")
    private String position;

    @NotNull(message = "salary cant be null")
    @Positive(message = "salary must be a positive number")
    private BigDecimal salary;
}
