package com.example.tabadol.restControllers;


import com.example.tabadol.JsonClasses.*;
import com.example.tabadol.contoller.Views;
import com.example.tabadol.model.Post;
import com.example.tabadol.model.UserApplication;
import com.example.tabadol.repository.PostRepository;
import com.example.tabadol.repository.UserApplicationRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class RestUserApplicationController {

    @Autowired
    UserApplicationRepository userApplicationRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    PostRepository postRepository;



    @PostMapping("/jsignup")
    @ResponseBody
    public ResponseEntity signup_j(@RequestBody SignupFormJson signup)
    {
        final String imageUrl = "https://res.cloudinary.com/saify/image/upload/v1539009756/icon.jpg";
        //if the user did not enter an url image

        if( !signup.isValidPhone())
            return new ResponseEntity(new ResponseJson("Phone number not valid!"), HttpStatus.EXPECTATION_FAILED);
        if( !signup.isValidEmail())
            return new ResponseEntity(new ResponseJson("Email not valid!"),HttpStatus.EXPECTATION_FAILED);
        if( ! signup.isAllFieldsEntered() )
            return new ResponseEntity(new ResponseJson("you must enter all fields.."),HttpStatus.EXPECTATION_FAILED);
        try {
            if( signup.getImage() == null || signup.getImage().length() == 0 )
                signup.setImage(imageUrl);
            UserApplication newUser = new UserApplication(signup.getUsername(),signup.getEmail(),signup.getFirstname(),signup.getLastname(),
                    bCryptPasswordEncoder.encode(signup.getPassword()),signup.getSkills(),signup.getBio(),signup.getPhone(), signup.getImage());
            userApplicationRepository.save(newUser);
            return new ResponseEntity(new ResponseJson("The account registered successfully"),HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity(new ResponseJson("Email or Username is already exist!"),HttpStatus.EXPECTATION_FAILED);
        }
    }



    @GetMapping("/jfollowinglist/{username}")
    @ResponseBody
    public Set<UserJson> getFollowingList_jj(Principal p, @PathVariable String username, Model m) {
        UserApplication user = userApplicationRepository.findByUsername(username);
        Set<UserApplication> users =  user.getUsers_I_follow();
        Set<UserJson> usersJ = users.stream().map(u -> new UserJson(u.getId(),u.getUsername(),u.getEmail(),u.getFirstname(),u.getLastname(),u.getSkills(),u.getBio(),u.getNumberOfFollowers(),u.getUsers_I_follow().size(),u.getRating(),u.getPhone(),u.getImage())).collect(Collectors.toSet());
        return usersJ;
    }


    @GetMapping("/jfollowerslist/{username}")
    @ResponseBody
    public List<UserJson> getFollowersList_j(@PathVariable String username, Model m, Principal p) {
        UserApplication user = userApplicationRepository.findByUsername(username);
        List<Long> followersIDs = userApplicationRepository.findAllByFollowing_user(user.getId());
        List<UserApplication> followers = followersIDs.stream().map(id -> userApplicationRepository.findById(id).get()).collect(Collectors.toList());
        List<UserJson> usersJ = followers.stream().map(u -> new UserJson(u.getId(),u.getUsername(),u.getEmail(),u.getFirstname(),u.getLastname(),u.getSkills(),u.getBio(),u.getNumberOfFollowers(),u.getUsers_I_follow().size(),u.getRating(),u.getPhone(),u.getImage())).collect(Collectors.toList());
        return usersJ;
    }


    @GetMapping("/jallUsers")
    @ResponseBody
    public List<UserJson> getAllusers_jj(Principal p, Model m) {
        List<UserApplication> users = userApplicationRepository.findAll();
        List<UserJson> usersJ = users.stream().map(u -> new UserJson(u.getId(),u.getUsername(),u.getEmail(),u.getFirstname(),u.getLastname(),u.getSkills(),u.getBio(),u.getNumberOfFollowers(),u.getUsers_I_follow().size(),u.getRating(),u.getPhone(),u.getImage())).collect(Collectors.toList());
        return usersJ;
    }



    @GetMapping("/jprofile/{id}")
    @JsonView(Views.ProfileView.class)
    @ResponseBody
    public UserJson getUserProfile_jj(Principal p, Model m, @PathVariable long id) {
        UserApplication user = userApplicationRepository.findById(id).get();
        List<Post> posts = user.getPosts().stream().filter(post-> post.isAvailable()).collect(Collectors.toList());
        UserJson userJ = new UserJson(user.getId(),user.getUsername(),user.getEmail(),user.getFirstname(),user.getLastname(),user.getSkills(),user.getBio(),user.getNumberOfFollowers(),user.getUsers_I_follow().size(),user.getRating(),user.getPhone(),user.getImage(),posts);

        return userJ;
    }


    @GetMapping("/jmyprofile")
    @JsonView(Views.ProfileView.class)
    @ResponseBody
    public UserJson getMyProfile_j(Principal p, Model m) {
        UserApplication user = userApplicationRepository.findByUsername(p.getName());
        List<Post> posts = user.getPosts().stream().filter(post-> post.isAvailable()).collect(Collectors.toList());
        UserJson userJ = new UserJson(user.getId(),user.getUsername(),user.getEmail(),user.getFirstname(),user.getLastname(),user.getSkills(),user.getBio(),user.getNumberOfFollowers(),user.getUsers_I_follow().size(),user.getRating(),user.getPhone(),user.getImage(),posts);
        return userJ;
    }


    @PostMapping("/jfollow/{username}")
    @ResponseBody
    public ResponseEntity followUser_j(Principal p, @PathVariable String username) {
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
        UserApplication userToFollow = userApplicationRepository.findByUsername(username);
        loggedInUser.followUser(userToFollow);
        userToFollow.increaseNumberOfFollowers();
        userApplicationRepository.save(loggedInUser);
        userApplicationRepository.save(userToFollow);
        String message = "you've followed ("+userToFollow.getUsername()+") successfully";
        return new ResponseEntity(new ResponseJson(message),HttpStatus.OK);
    }

    @PostMapping("/junfollow/{username}")
    @ResponseBody
    public ResponseEntity unFollowUser_j(Principal p, @PathVariable String username) {
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
        UserApplication userToUnFollow = userApplicationRepository.findByUsername(username);
        loggedInUser.unfollowUser(userToUnFollow);
        userToUnFollow.decreaseNumberOfFollowers();
        userApplicationRepository.save(loggedInUser);
        userApplicationRepository.save(userToUnFollow);
        String message = "you've unfollowed ("+ userToUnFollow.getUsername()+ ") successfully";
        return new ResponseEntity(new ResponseJson(message),HttpStatus.OK);
    }

    @PutMapping("/jedit-profile")
    @JsonView(Views.ProfileView.class)
    @ResponseBody
    public UserJson editProfile(Principal p, @RequestBody EditPostFormJson fields){

        final String imageUrl = "https://res.cloudinary.com/saify/image/upload/v1539009756/icon.jpg";
        //if the user did not enter an url image
        if(fields.getImage() == null ||  fields.getImage().length() == 0)
            fields.setImage(imageUrl);
        UserApplication userToEdit = userApplicationRepository.findByUsername(p.getName());
        if(fields.getFirstname().length() != 0)
            userToEdit.setFirstname(fields.getFirstname());

        if(fields.getLastname().length() != 0)
            userToEdit.setLastname(fields.getLastname());

        if(fields.getSkills().length() != 0)
            userToEdit.setSkills(fields.getSkills());

        if(fields.getBio().length() != 0)
            userToEdit.setBio(fields.getBio());

        if(fields.getPhone().length() != 0)
            userToEdit.setPhone(fields.getPhone());

        if(fields.getImage().length() != 0)
            userToEdit.setImage(fields.getImage());

        userApplicationRepository.save(userToEdit);
        UserJson userJ = new UserJson(userToEdit.getId(),userToEdit.getUsername(),userToEdit.getEmail(),userToEdit.getFirstname(),userToEdit.getLastname(),userToEdit.getSkills(),userToEdit.getBio(),userToEdit.getNumberOfFollowers(),userToEdit.getUsers_I_follow().size(),userToEdit.getRating(),userToEdit.getPhone(),userToEdit.getImage(),userToEdit.getPosts());
        return userJ;
    }


    @PostMapping("/jrate/{username}")
    public ResponseEntity getRating(@RequestBody RateUserJson rateJson, @PathVariable String username, Principal p){
        UserApplication userToBeRated = userApplicationRepository.findByUsername(username);
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
        long usersId= userToBeRated.getId();


        if(loggedInUser.didRateTheUser(userToBeRated))
            return new ResponseEntity(new ResponseJson("You Already have rated this user") ,HttpStatus.OK);

        loggedInUser.rateUser(userToBeRated);
        userToBeRated.addTOSumOfTotalRates(rateJson.getRateValue());
        userToBeRated.increaseNumberOfRaters();
        double rate = ((double)userToBeRated.getSumOfTotalRates()/(double) userToBeRated.getNumberOfRaters());
        DecimalFormat decimalFormat = new DecimalFormat("#0.##");
        rate = Double.parseDouble(decimalFormat.format(rate));
        userToBeRated.setRating(rate);
        userApplicationRepository.save(userToBeRated);
        userApplicationRepository.save(loggedInUser);
        String message = "You'v reted the user ("+userToBeRated.getUsername()+") successfully";
        return new ResponseEntity(new ResponseJson(message) ,HttpStatus.OK);
    }


    @GetMapping("/jratedusers")
    public RatedUsersJson getMyRatedUsersList(Principal p){
        UserApplication user = userApplicationRepository.findByUsername(p.getName());
        List<Long> ids = new ArrayList<>();
        ids = user.getRates().stream().map(u -> u.getId()).collect(Collectors.toList());

        return new RatedUsersJson(ids);
    }

    @DeleteMapping("/jdeleteuser/{id}")
    public ResponseJson deleteUserById(@PathVariable("id") long id){

        try {
            userApplicationRepository.deleteById(id);
            return new ResponseJson("Usere Deleted");
        }
        catch (Exception e){
            return new ResponseJson("Usere Not Found");
        }
    }



}
// check about next lines..
//    ApplicationUser newUser = new ApplicationUser(username,passwordEncoder.encode(password),firstName,lastName,dateOfBirth,bio);
//    newUser = applicationUserRepository.save(newUser);
//    Authentication authentication = new UsernamePasswordAuthenticationToken(newUser,null,new ArrayList<>());
//        SecurityContextHolder.getContext().setAuthentication(authentication);


