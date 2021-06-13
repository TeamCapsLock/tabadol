package com.example.tabadol.contoller;


import com.example.tabadol.JsonClasses.LoginForm;
import com.example.tabadol.JsonClasses.ResponseJson;
import com.example.tabadol.JsonClasses.SignupFormJson;
import com.example.tabadol.JsonClasses.UserJson;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.View;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;
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
    public String getSingUpForm(Principal p, Model m) {
//        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
//        m.addAttribute("loggedInUser", loggedInUser);
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView signup(String username, String email, String firstname,
                               String lastname, String password, String confirm,
                               String skills, String bio, String phone, String image)
    {
            final String imageUrl = "https://res.cloudinary.com/saify/image/upload/v1539009756/icon.jpg";
        //if the user did not enter an url image
            if(image.length() == 0)
                image = imageUrl;
            UserApplication newUser = new UserApplication(username,email,firstname,lastname,
                    bCryptPasswordEncoder.encode(password),skills,bio,phone, image);
            userApplicationRepository.save(newUser);

            return new RedirectView("/login");

    }

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


    @GetMapping("/")
    public String getHome(Model m,Principal p) {
        UserApplication loggedInUser = null;
        try{
              loggedInUser = userApplicationRepository.findByUsername(p.getName());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        m.addAttribute("loggedInUser",loggedInUser);
        return "index.html";
    }

        @GetMapping("/login")
        public String loginForm(Principal p, Model m) {
//            UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
//            m.addAttribute("loggedInUser", loggedInUser);
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

    @PostMapping("/unfollow/{username}")
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


//    @GetMapping("/jfollowinglist/{username}")
//    @ResponseBody
//    @JsonView(Views.UserView.class)
//    public Set<UserApplication> getFollowingList_j(Principal p, @PathVariable String username, Model m) {
//        UserApplication user = userApplicationRepository.findByUsername(username);
////        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
//
//        Set<UserApplication> users =  user.getUsers_I_follow();
////        m.addAttribute("users", user.getUsers_I_follow());
////        m.addAttribute("loggedInUser", loggedInUser);
////        m.addAttribute("userWithTheList", username);
//
//        return users;
//    }



    @GetMapping("/jfollowinglist/{username}")
    @ResponseBody
    public Set<UserJson> getFollowingList_jj(Principal p, @PathVariable String username, Model m) {
        UserApplication user = userApplicationRepository.findByUsername(username);
        Set<UserApplication> users =  user.getUsers_I_follow();
        Set<UserJson> usersJ = users.stream().map(u -> new UserJson(u.getId(),u.getUsername(),u.getEmail(),u.getFirstname(),u.getLastname(),u.getSkills(),u.getBio(),u.getNumberOfFollowers(),u.getUsers_I_follow().size(),u.getRating(),u.getPhone(),u.getImage())).collect(Collectors.toSet());
        return usersJ;
    }




    @GetMapping("/followerslist/{username}")
    public String getFollowersList(@PathVariable String username, Model m, Principal p) {
        UserApplication user = userApplicationRepository.findByUsername(username);
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
        List<Long> followersIDs = userApplicationRepository.findAllByFollowing_user(user.getId());
        List<UserApplication> followers = followersIDs.stream().map(id -> userApplicationRepository.findById(id).get()).collect(Collectors.toList());

 // another way of doing it
//        List<UserApplication> followers2 = new ArrayList<>();
//        for( long id : followersIDs){
//            followers.add(userApplicationRepository.findById(id).get());
//        }
        m.addAttribute("loggedInUser", loggedInUser);
        m.addAttribute("users", followers);
        m.addAttribute("userWithTheList", username);
        return "followers";
    }

//    @GetMapping("/jfollowerslist/{username}")
//    @ResponseBody
//    @JsonView(Views.UserView.class)
//    public List<UserApplication> getFollowersList_j(@PathVariable String username, Model m, Principal p) {
//        UserApplication user = userApplicationRepository.findByUsername(username);
//        List<Long> followersIDs = userApplicationRepository.findAllByFollowing_user(user.getId());
//        List<UserApplication> followers = followersIDs.stream().map(id -> userApplicationRepository.findById(id).get()).collect(Collectors.toList());
//
//        return followers;
//    }

    @GetMapping("/jfollowerslist/{username}")
    @ResponseBody
    public List<UserJson> getFollowersList_j(@PathVariable String username, Model m, Principal p) {
        UserApplication user = userApplicationRepository.findByUsername(username);
        List<Long> followersIDs = userApplicationRepository.findAllByFollowing_user(user.getId());
        List<UserApplication> followers = followersIDs.stream().map(id -> userApplicationRepository.findById(id).get()).collect(Collectors.toList());
        List<UserJson> usersJ = followers.stream().map(u -> new UserJson(u.getId(),u.getUsername(),u.getEmail(),u.getFirstname(),u.getLastname(),u.getSkills(),u.getBio(),u.getNumberOfFollowers(),u.getUsers_I_follow().size(),u.getRating(),u.getPhone(),u.getImage())).collect(Collectors.toList());
        return usersJ;
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

//    @GetMapping("/jallUsers")
//    @JsonView(Views.UserView.class)
//    @ResponseBody
//    public List<UserApplication> getAllusers_j(Principal p, Model m) {
////        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
//        List<UserApplication> users = userApplicationRepository.findAll();
////        m.addAttribute("users", users);
////        m.addAttribute("myUsername", p.getName());
////        m.addAttribute("loggedInUser", loggedInUser);
//        return users;
//    }


    @GetMapping("/jallUsers")
    @ResponseBody
    public List<UserJson> getAllusers_jj(Principal p, Model m) {
        List<UserApplication> users = userApplicationRepository.findAll();
        List<UserJson> usersJ = users.stream().map(u -> new UserJson(u.getId(),u.getUsername(),u.getEmail(),u.getFirstname(),u.getLastname(),u.getSkills(),u.getBio(),u.getNumberOfFollowers(),u.getUsers_I_follow().size(),u.getRating(),u.getPhone(),u.getImage())).collect(Collectors.toList());
        return usersJ;
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

//    @GetMapping("/jprofile/{id}")
//    @ResponseBody
//    @JsonView(Views.ProfileView.class)
//    public UserApplication getUserProfile_j(Principal p, Model m, @PathVariable long id) {
////        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
//        UserApplication user = userApplicationRepository.findById(id).get();
////        m.addAttribute("user", user);
////        m.addAttribute("posts", postRepository.findAllByUser_id(user.getId()));
////        m.addAttribute("loggedInUser", loggedInUser);
//        return user;
//    }

    @GetMapping("/jprofile/{id}")
    @JsonView(Views.ProfileView.class)
    @ResponseBody
    public UserJson getUserProfile_jj(Principal p, Model m, @PathVariable long id) {
        UserApplication user = userApplicationRepository.findById(id).get();
        UserJson userJ = new UserJson(user.getId(),user.getUsername(),user.getEmail(),user.getFirstname(),user.getLastname(),user.getSkills(),user.getBio(),user.getNumberOfFollowers(),user.getUsers_I_follow().size(),user.getRating(),user.getPhone(),user.getImage(),user.getPosts());

        return userJ;
    }




    @GetMapping("/myprofile")
    public RedirectView getMyProfile(Principal p, Model m) {
        UserApplication user = userApplicationRepository.findByUsername(p.getName());
        long id = user.getId();
        return new RedirectView("/profile/" + id);
    }

    @GetMapping("/jmyprofile")
    @JsonView(Views.ProfileView.class)
    @ResponseBody
    public UserJson getMyProfile_j(Principal p, Model m) {
        UserApplication user = userApplicationRepository.findByUsername(p.getName());
        UserJson userJ = new UserJson(user.getId(),user.getUsername(),user.getEmail(),user.getFirstname(),user.getLastname(),user.getSkills(),user.getBio(),user.getNumberOfFollowers(),user.getUsers_I_follow().size(),user.getRating(),user.getPhone(),user.getImage(),user.getPosts());
        return userJ;
    }

    @PostMapping("/edit-profile/{id}")
    public RedirectView editProfile(@PathVariable(value="id") long id,String firstname, String lastname, String skills, String bio, String phone, String image){

        final String imageUrl = "https://res.cloudinary.com/saify/image/upload/v1539009756/icon.jpg";
        //if the user did not enter an url image
        if(image.length() == 0)
            image = imageUrl;
        UserApplication userToEdit = userApplicationRepository.findById(id).get();
        userToEdit.setFirstname(firstname);
        userToEdit.setLastname(lastname);
        userToEdit.setSkills(skills);
        userToEdit.setBio(bio);
        userToEdit.setPhone(phone);
        userToEdit.setImage(image);
        userApplicationRepository.save(userToEdit);
        return new RedirectView("/myprofile");
    }


    @PostMapping("/rate/{username}")
        public RedirectView getRating(int sumOfTotalRates, @PathVariable String username, Principal p){
        UserApplication userToBeRated = userApplicationRepository.findByUsername(username);
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
        long usersId= userToBeRated.getId();

        loggedInUser.rateUser(userToBeRated);

        userToBeRated.addTOSumOfTotalRates(sumOfTotalRates);
        userToBeRated.increaseNumberOfRaters();
       double rate = ((double)userToBeRated.getSumOfTotalRates()/(double) userToBeRated.getNumberOfRaters());
        DecimalFormat decimalFormat = new DecimalFormat("#0.##");
        rate = Double.parseDouble(decimalFormat.format(rate));
        userToBeRated.setRating(rate);
        userApplicationRepository.save(userToBeRated);
        userApplicationRepository.save(loggedInUser);
        return new RedirectView("/profile/" + usersId);
        }






}
// check about next lines..
//    ApplicationUser newUser = new ApplicationUser(username,passwordEncoder.encode(password),firstName,lastName,dateOfBirth,bio);
//    newUser = applicationUserRepository.save(newUser);
//    Authentication authentication = new UsernamePasswordAuthenticationToken(newUser,null,new ArrayList<>());
//        SecurityContextHolder.getContext().setAuthentication(authentication);


