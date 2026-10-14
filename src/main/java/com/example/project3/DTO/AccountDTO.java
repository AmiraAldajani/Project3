package com.example.project3.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    @NotNull(message = "account number can't be null")
    @Pattern(regexp = "^\\d{4}-\\d{4}-\\d{4}-\\d{4}$",
            message = "account number must follow the format XXXX-XXXX-XXXX-XXXX")
    private String accountNumber;

    @NotNull(message = "balance can't be null")
    @DecimalMin(value = "0.0", message = "Balance must be non-negative")
    private BigDecimal balance;
}
