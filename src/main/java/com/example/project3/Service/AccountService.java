package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.AccountDTO;
import com.example.project3.DTO.AccountUpdateDTO;
import com.example.project3.Model.Account;
import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import com.example.project3.Repository.AccountRepository;
import com.example.project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public List<Account> getMyAccounts(User user) {
        return accountRepository.findAccountsByCustomer(getCustomerOf(user)); //line 28
    }

    private Customer getCustomerOf(User user) {
        Customer customer = customerRepository.findCustomerByUser(user);
        if (customer == null) {
            throw new ApiException("customer not found");
        }
        return customer;
    }

    public void createAccount(User user, AccountDTO dto){
        Customer customer = getCustomerOf(user);
        if (accountRepository.existsByAccountNumber(dto.getAccountNumber()))
            throw new ApiException("account number already exists");
        Account account = new Account();
        account.setAccountNumber(dto.getAccountNumber());
        account.setBalance(dto.getBalance());
        account.setIsActive(false);
        account.setCustomer(customer);
        accountRepository.save(account);
    }

    public Account getAccount(User user, Integer accountId){
        if("customer".equals(user.getRole()))
            return getOwnedAccount(user, accountId);
        return  getAccountOrThrow(accountId);
    }

    private Account getOwnedAccount(User principal, Integer accountId) {
        Account account = getAccountOrThrow(accountId);
        if (!account.getCustomer().getUser().getId().equals(principal.getId())) {
            throw new ApiException("this account does not belong to you");
        }
        return account;
    }

    private Account getAccountOrThrow(Integer accountId) {
        Account account = accountRepository.findAccountById(accountId);
        if (account == null) {
            throw new ApiException("account not found");
        }
        return account;
    }

    public void updateAccount(User user, Integer accountId, AccountUpdateDTO dto) {
        Account account = getOwnedAccount(user, accountId);
        if (!account.getAccountNumber().equals(dto.getAccountNumber())
                && accountRepository.existsByAccountNumber(dto.getAccountNumber())) {
            throw new ApiException("account number already exists");
        }
        account.setAccountNumber(dto.getAccountNumber());
        accountRepository.save(account);
    }


    public void deleteAccount(User user, Integer accountId) {
        Account account = getOwnedAccount(user, accountId);
        if (account.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            throw new ApiException("withdraw or transfer the remaining balance before deleting the account");
        }
        accountRepository.delete(account);
    }

    public void activateAccount(Integer accountId) {
        Account account = getAccountOrThrow(accountId);
        if (account.getIsActive()) {
            throw new ApiException("account is already active");
        }
        account.setIsActive(true);
        accountRepository.save(account);
    }

    public void blockAccount(Integer accountId) {
        Account account = getAccountOrThrow(accountId);
        if (!account.getIsActive()) {
            throw new ApiException("account is already blocked");
        }
        account.setIsActive(false);
        accountRepository.save(account);
    }

    public void deposit(User principal, Integer accountId, BigDecimal amount) {
        checkAmount(amount);
        Account account = getOwnedAccount(principal, accountId);
        checkActive(account);
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }
    private void checkAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApiException("amount must be greater than zero");
        }
    }

    private void checkActive(Account account) {
        if (!account.getIsActive()) {
            throw new ApiException("account is not active");
        }
    }

    public void withdraw(User principal, Integer accountId, BigDecimal amount) {
        checkAmount(amount);
        Account account = getOwnedAccount(principal, accountId);
        checkActive(account);
        checkFunds(account, amount);
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }
    private void checkFunds(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new ApiException("insufficient balance");
        }
    }

    public void transfer(User principal, Integer fromId, Integer toId, BigDecimal amount) {
        checkAmount(amount);
        if (fromId.equals(toId)) {
            throw new ApiException("cant transfer to the same account");
        }
        Account from = getOwnedAccount(principal, fromId);
        Account to = getAccountOrThrow(toId);
        checkActive(from);
        if (!to.getIsActive()) {
            throw new ApiException("destination account is not active");
        }
        checkFunds(from, amount);

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));
        accountRepository.save(from);
        accountRepository.save(to);
    }
}
