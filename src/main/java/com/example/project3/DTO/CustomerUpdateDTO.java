package com.example.project3.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateDTO {

    @NotEmpty(message = "name cant be null")
    @Size( max = 50, message = "name length must be less than 50 characters")
    private String name;

    @NotEmpty(message = "email cant be null")
    @Email(message = "email must be a valid email format")
    private String email;

    @NotEmpty(message = "phone number cant be null")
    @Pattern(regexp = "^05\\d{8}$", message = "phone number must start with 05 and be 10 digits")
    private String phoneNumber;
}