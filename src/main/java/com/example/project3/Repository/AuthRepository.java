package com.example.project3.Repository;

import com.example.project3.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User,Integer> {
    User findByUsername (String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User findUserById (Integer id);
}
