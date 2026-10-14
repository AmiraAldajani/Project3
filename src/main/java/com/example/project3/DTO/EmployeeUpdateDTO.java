package com.example.project3.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUpdateDTO {

    @NotEmpty(message = "name cant be null")
    @Size(min = 2, max = 20, message = "name length must be between 2 and 20 characters")
    private String name;

    @NotEmpty(message = "email cant be null")
    @Email(message = "email must be a valid email format")
    private String email;

    @NotEmpty(message = "position cant be null")
    private String position;

    @NotNull(message = "salary cant be null")
    @PositiveOrZero(message = "salary must be a non-negative number")
    private BigDecimal salary;
}
