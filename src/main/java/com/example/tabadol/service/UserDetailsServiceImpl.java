package com.example.tabadol.service;

import com.example.tabadol.model.UserApplication;
import com.example.tabadol.repository.UserApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserApplicationRepository userApplicationRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


//        TODO: Add the user as a commented line.

        UserApplication userApplication = userApplicationRepository.findByUsername(username);

        if(userApplication == null){
            System.out.println("unable to find user");
            throw new UsernameNotFoundException("can't find user:  "+username);
        }
        System.out.println("User "+ username + " found");
        return userApplication;
    }


}
