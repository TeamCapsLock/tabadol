package com.example.tabadol.contoller;


import com.example.tabadol.model.UserApplication;
import com.example.tabadol.repository.PostRepository;
import com.example.tabadol.repository.UserApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserApplicationController {

    @Autowired
    UserApplicationRepository userApplicationRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    PostRepository postRepository;


    @GetMapping("/signup")
    public String getSingUpForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView signup(String username, String email, String firstname,
                               String lastname, String password, String confirm,
                               String skills, String bio) {
        if (password.equals(confirm)) {
            UserApplication newUser = new UserApplication(username, email, firstname, lastname, bCryptPasswordEncoder.encode(password), skills, bio);
            userApplicationRepository.save(newUser);

            return new RedirectView("/login");
        }


        return new RedirectView("/signup");
    }

    @GetMapping("/")
    public String getHome() {

        return "home.html";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login.html";
    }


    @PostMapping("/follow/{username}")
    public RedirectView followUser(Principal p, @PathVariable String username, @RequestParam String route) {
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
        UserApplication userToFollow = userApplicationRepository.findByUsername(username);
        loggedInUser.followUser(userToFollow);
        userToFollow.increaseNumberOfFollowers();
        userApplicationRepository.save(loggedInUser);
        userApplicationRepository.save(userToFollow);
        return new RedirectView(route);
    }

    @DeleteMapping("/unfollow/{username}")
    public RedirectView unFollowUser(Principal p, @PathVariable String username, @RequestParam String route) {
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
        UserApplication userToUnFollow = userApplicationRepository.findByUsername(username);
        loggedInUser.unfollowUser(userToUnFollow);
        userToUnFollow.decreaseNumberOfFollowers();
        userApplicationRepository.save(loggedInUser);
        userApplicationRepository.save(userToUnFollow);
        return new RedirectView(route);
    }

    @GetMapping("/followinglist/{username}")
    public String getFollowingList(Principal p, @PathVariable String username, Model m) {
        UserApplication user = userApplicationRepository.findByUsername(username);
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
        m.addAttribute("users", user.getUsers_I_follow());
        m.addAttribute("loggedInUser", loggedInUser);
        m.addAttribute("userWithTheList", username);

        return "followingUsers";
    }

    @GetMapping("/followerslist/{username}")
    public String getFollowersList(@PathVariable String username, Model m, Principal p) {
        UserApplication user = userApplicationRepository.findByUsername(username);
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
        List<Long> followersIDs = userApplicationRepository.findAllByFollowing_user(user.getId());
//        List<UserApplication> followers2 = new ArrayList<>();
//        for( long id : followersIDs){
//            followers.add(userApplicationRepository.findById(id).get());
//        }
        List<UserApplication> followers = followersIDs.stream().map(id -> userApplicationRepository.findById(id).get()).collect(Collectors.toList());
        m.addAttribute("loggedInUser", loggedInUser);
        m.addAttribute("users", followers);
        m.addAttribute("userWithTheList", username);
        return "followers";
    }


    @GetMapping("/allUsers")
    public String getAllusers(Principal p, Model m) {
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
        List<UserApplication> users = userApplicationRepository.findAll();
        m.addAttribute("users", users);
        m.addAttribute("myUsername", p.getName());
        m.addAttribute("loggedInUser", loggedInUser);
        return "allProfiles";
    }


    @GetMapping("/profile/{id}")
    public String getUserProfile(Principal p, Model m, @PathVariable long id) {
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
        UserApplication user = userApplicationRepository.findById(id).get();
        m.addAttribute("user", user);
        m.addAttribute("posts", postRepository.findAllByUser_id(user.getId()));
        m.addAttribute("loggedInUser", loggedInUser);
        return "profile";
    }


    @GetMapping("/myprofile")
    public RedirectView getMyProfile(Principal p, Model m) {
        UserApplication user = userApplicationRepository.findByUsername(p.getName());
        long id = user.getId();
        return new RedirectView("/profile/" + id);
    }


}
// check about next lines..
//    ApplicationUser newUser = new ApplicationUser(username,passwordEncoder.encode(password),firstName,lastName,dateOfBirth,bio);
//    newUser = applicationUserRepository.save(newUser);
//    Authentication authentication = new UsernamePasswordAuthenticationToken(newUser,null,new ArrayList<>());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
