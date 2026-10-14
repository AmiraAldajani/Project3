package com.example.project3.Repository;

import com.example.project3.Model.Employee;
import com.example.project3.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findEmployeeByUser(User user);
    Employee findEmployeeById(Integer id);
}
