package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.CustomerUpdateDTO;
import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    public Customer getMyProfile(User user) {
        return getCustomerOf(user);
    }
    private Customer getCustomerOf(User user) {
        Customer customer = customerRepository.findCustomerByUser(user);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        return customer;
    }

    //by admin
    public void deleteCustomer(Integer customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        customerRepository.delete(customer);
    }
    // by user
    public void deleteMyAccount(User user) {
        customerRepository.delete(getCustomerOf(user));
    }

    public void updateProfile(User user, CustomerUpdateDTO dto){
        Customer customer = getCustomerOf(user);
        User userToUpdate = customer.getUser();
        if (!userToUpdate.getEmail().equalsIgnoreCase(dto.getEmail())&& authRepository.existsByEmail(dto.getEmail()))
            throw new ApiException("Email already exists");
        userToUpdate.setName(dto.getName());
        userToUpdate.setEmail(dto.getEmail());
        customer.setPhoneNumber(dto.getPhoneNumber());
        authRepository.save(userToUpdate);
        customerRepository.save(customer);
    }

}
