package com.example.project3.Repository;

import com.example.project3.Model.Account;
import com.example.project3.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Integer> {
    List<Account> findAccountsByCustomer(Customer customer);
    boolean existsByAccountNumber(String accountNumber);
    Account findAccountById(Integer id);
}
