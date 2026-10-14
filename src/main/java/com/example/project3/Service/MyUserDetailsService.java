package com.example.project3.Service;

import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// نسوي لود لليوزر
@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final AuthRepository authRepository;

    //امرر لهذي الميثود الشي الي بدخل منه authentication
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = authRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("wrong username or password");
        }
        return user;
    }
}