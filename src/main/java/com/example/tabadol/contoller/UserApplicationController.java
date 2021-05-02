package com.example.tabadol.contoller;


import com.example.tabadol.repository.UserApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @Autowired
    UserApplicationRepository userApplicationRepository;





    @GetMapping("/")
    public String getHome(){

        return "home.html";
    }


}
