package com.example.tabadol.contoller;


import com.example.tabadol.model.UserApplication;
import com.example.tabadol.repository.PostRepository;
import com.example.tabadol.repository.UserApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;
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

    

    @PostMapping("/follow/{username}")
    public RedirectView followUser(Principal p, @PathVariable String username, @RequestParam String route){
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
        UserApplication userToFollow = userApplicationRepository.findByUsername(username);

        loggedInUser.followUser(userToFollow);
         userToFollow.increaseNumberOfFollowers();
        userApplicationRepository.save(loggedInUser);
        userApplicationRepository.save(userToFollow);
        return new RedirectView(route);
    }

    @DeleteMapping ("/unfollow/{username}")
    public RedirectView unFollowUser(Principal p, @PathVariable String username, @RequestParam String route){
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
        UserApplication userToUnFollow = userApplicationRepository.findByUsername(username);
        loggedInUser.unfollowUser(userToUnFollow);
        userToUnFollow.decreaseNumberOfFollowers();
        userApplicationRepository.save(loggedInUser);
        userApplicationRepository.save(userToUnFollow);
        return new RedirectView(route);
    }



    @GetMapping("/allUsers")
    public String getAllusers(Principal p, Model m){
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
        List<UserApplication> users = userApplicationRepository.findAll();
        m.addAttribute("users",users);
        m.addAttribute("myUsername",p.getName());
        m.addAttribute("loggedInUser",loggedInUser);
        return "allProfiles";
    }


    @GetMapping("/profile/{id}")
    public String getUserProfile(Principal p,Model m,@PathVariable long id){
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
        UserApplication user = userApplicationRepository.findById(id).get();
        m.addAttribute("user",user);
        m.addAttribute("posts", postRepository.findAllByUser_id(user.getId()));
        m.addAttribute("loggedInUser",loggedInUser);
        return "profile";
    }



    @GetMapping("/myprofile")
    public RedirectView getMyProfile(Principal p, Model m){
        UserApplication user = userApplicationRepository.findByUsername(p.getName());
        long id = user.getId();
        return new RedirectView("/profile/"+id);
    }


}
// check about next lines..
//    ApplicationUser newUser = new ApplicationUser(username,passwordEncoder.encode(password),firstName,lastName,dateOfBirth,bio);
//    newUser = applicationUserRepository.save(newUser);
//    Authentication authentication = new UsernamePasswordAuthenticationToken(newUser,null,new ArrayList<>());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
