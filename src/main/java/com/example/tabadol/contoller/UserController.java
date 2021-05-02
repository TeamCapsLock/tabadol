package com.example.tabadol.contoller;


import com.example.tabadol.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;





    @GetMapping("/")
    public String getHome(){

        return "home.html";
    }


}
