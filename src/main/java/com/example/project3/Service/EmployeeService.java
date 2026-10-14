package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.EmployeeUpdateDTO;
import com.example.project3.Model.Employee;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AuthRepository authRepository;

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee getMyProfile(User user){
        Employee employee = employeeRepository.findEmployeeByUser(user);
        if (employee == null) {
            throw new ApiException("employee not found");
        }
        return employee;
    }

    public void deleteEmployee(Integer employeeId){
        Employee employee = employeeRepository.findEmployeeById(employeeId);
        if(employee == null)
            throw new ApiException("Employee not found");
        employeeRepository.delete(employee);
    }

    public void updateEmployee(Integer employeeId, EmployeeUpdateDTO dto){
        Employee employee = employeeRepository.findEmployeeById(employeeId);
        if (employee== null)
            throw new ApiException("employee not found");
        User user = employee.getUser();
        if (!user.getEmail().equalsIgnoreCase(dto.getEmail()) && authRepository.existsByEmail(dto.getEmail()))
            throw new ApiException("email already exists");

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        employee.setPosition(dto.getPosition());
        employee.setSalary(dto.getSalary());
        authRepository.save(user);
        employeeRepository.save(employee);
    }
}
