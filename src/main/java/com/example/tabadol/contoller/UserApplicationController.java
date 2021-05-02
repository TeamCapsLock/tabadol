package com.example.tabadol.contoller;


import com.example.tabadol.model.UserApplication;
import com.example.tabadol.repository.PostRepository;
import com.example.tabadol.repository.UserApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class UserApplicationController {

    @Autowired
    UserApplicationRepository userApplicationRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    PostRepository postRepository;



    @GetMapping("/signup")
    public String getSingUpForm(){
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView signup(String username, String email, String firstname,
                               String lastname, String password, String confirm,
                               String skills, String bio)
    {
        if(password.equals( confirm)){
            UserApplication newUser = new UserApplication(username,email,firstname,lastname,bCryptPasswordEncoder.encode(password),skills,bio);
            userApplicationRepository.save(newUser);

            return new RedirectView("/login");
        }


        return new RedirectView("/signup");
    }

    @GetMapping("/")
    public String getHome(){

        return "home.html";
    }

    @GetMapping("/login")
    public String loginForm(){
        return "login.html";
    }

    @GetMapping("/myprofile")
    public String getUserProfilePage(Principal p, Model m){
        UserApplication currentUser = userApplicationRepository.findByUsername(p.getName());
        m.addAttribute("user", ((UsernamePasswordAuthenticationToken)p).getPrincipal());
        m.addAttribute("posts", postRepository.findByUserId(currentUser.getId()));
        return "profile.html";
    }

//    @GetMapping("/profiles/{id}")
//    public String getUserProfile(Principal p, Model m,@PathVariable Integer id){
//        DBUser requiredProfile = dbUserRepository.findById(id).get();
//        if(requiredProfile != null){
//            m.addAttribute("user", requiredProfile);
//            String loggedInUserName= p.getName();
//            DBUser loggedInUser = dbUserRepository.findByUsername(loggedInUserName);
//            boolean isAllowedToEdit = loggedInUser.getId() == id;
//            m.addAttribute("isAllowedToEdit",isAllowedToEdit);
//            return "profile.html";
//        } else {
//            m.addAttribute("message","this user doesn't exist");
//            return "error.html";
//        }
//    }


}
