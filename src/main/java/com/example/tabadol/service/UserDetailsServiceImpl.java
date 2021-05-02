package com.example.tabadol.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        TODO: Add the user as a commented line.
//        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);

//        if(applicationUser == null){
//            System.out.println("User not found");
//            throw new UsernameNotFoundException("User "+username+" does not exist");
//        }
//        System.out.println("User "+ username + " found");
//        return applicationUser;
//    }
        return null;
    }
}
