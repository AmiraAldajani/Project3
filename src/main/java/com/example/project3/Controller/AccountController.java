package com.example.project3.Controller;

import com.example.project3.Api.ApiResponse;
import com.example.project3.DTO.AccountDTO;
import com.example.project3.DTO.AccountUpdateDTO;
import com.example.project3.Model.User;
import com.example.project3.Service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequestMapping("/api/v1/account")
@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/createAccount")
    public ResponseEntity<?> createAccount(@AuthenticationPrincipal User user, @RequestBody @Valid AccountDTO dto) {
        accountService.createAccount(user, dto);
        return ResponseEntity.status(200).body(new ApiResponse("account created, waiting for activation"));
    }

    @PutMapping("/activateAccount/{accountId}")
    public ResponseEntity<?> activateAccount(@PathVariable Integer accountId) {
        accountService.activateAccount(accountId);
        return ResponseEntity.status(200).body(new ApiResponse("account activated"));
    }

    @PutMapping("/blockAccount/{accountId}")
    public ResponseEntity<?> blockAccount(@PathVariable Integer accountId) {
        accountService.blockAccount(accountId);
        return ResponseEntity.status(200).body(new ApiResponse("account blocked"));
    }

    @GetMapping("/getAccount/{accountId}")
    public ResponseEntity<?> getAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId) {
        return ResponseEntity.status(200).body(accountService.getAccount(user, accountId));
    }

    @GetMapping("/getMyAccounts")
    public ResponseEntity<?> getMyAccounts(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(accountService.getMyAccounts(user));
    }

    @GetMapping("/getAllAccounts")
    public ResponseEntity<?> getAllAccounts() {
        return ResponseEntity.status(200).body(accountService.getAllAccounts());
    }

    @PutMapping("/updateAccount/{accountId}")
    public ResponseEntity<?> updateAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId,
                                           @RequestBody @Valid AccountUpdateDTO dto) {
        accountService.updateAccount(user, accountId, dto);
        return ResponseEntity.status(200).body(new ApiResponse("account updated"));
    }

    @DeleteMapping("/deleteAccount/{accountId}")
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId) {
        accountService.deleteAccount(user, accountId);
        return ResponseEntity.status(200).body(new ApiResponse("account deleted"));
    }

    @PutMapping("/deposit/{accountId}/{amount}")
    public ResponseEntity<?> deposit(@AuthenticationPrincipal User user, @PathVariable Integer accountId,
                                     @PathVariable BigDecimal amount) {
        accountService.deposit(user, accountId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("deposit successful"));
    }

    @PutMapping("/withdraw/{accountId}/{amount}")
    public ResponseEntity<?> withdraw(@AuthenticationPrincipal User user, @PathVariable Integer accountId,
                                      @PathVariable BigDecimal amount) {
        accountService.withdraw(user, accountId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("withdraw successful"));
    }
    @PutMapping("/transfer/{fromAccountId}/{toAccountId}/{amount}")
    public ResponseEntity<?> transfer(@AuthenticationPrincipal User user, @PathVariable Integer fromAccountId,
                                      @PathVariable Integer toAccountId, @PathVariable BigDecimal amount) {
        accountService.transfer(user, fromAccountId, toAccountId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("transfer successful"));
    }
}
