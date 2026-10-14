package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.CustomerRegisterDTO;
import com.example.project3.DTO.EmployeeRegisterDTO;
import com.example.project3.Model.Customer;
import com.example.project3.Model.Employee;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.CustomerRepository;
import com.example.project3.Repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(String username, String password, String name, String email, String role) {
        if(authRepository.existsByUsername(username))
            throw new ApiException("Username already exists");
        if(authRepository.existsByEmail(email))
            throw new ApiException("Email already exists");
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);
        return authRepository.save(user);
    }
    @Transactional
    public void registerCustomer (CustomerRegisterDTO dto){
        User user = createUser(dto.getPhoneNumber(), dto.getPassword(), dto.getName(), dto.getEmail(), "customer");
        Customer customer = new Customer();
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setUser(user);
        customerRepository.save(customer);
    }
    @Transactional
    public void registerEmployee(EmployeeRegisterDTO dto) {
        User user = createUser(dto.getUsername(), dto.getPassword(), dto.getName(), dto.getEmail(), "employee");

        Employee employee = new Employee();
        employee.setPosition(dto.getPosition());
        employee.setSalary(dto.getSalary());
        employee.setUser(user);
        employeeRepository.save(employee);
    }


}
