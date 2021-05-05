package com.example.tabadol.contoller;

import com.example.tabadol.model.Post;
import com.example.tabadol.model.UserApplication;
import com.example.tabadol.repository.PostRepository;
import com.example.tabadol.repository.UserApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserApplicationRepository userApplicationRepository;


    @GetMapping("/posts")
    public String getPosts(Model m, Principal p){
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());


        m.addAttribute("posts",postRepository.findAll());
        m.addAttribute("loggedInUser",loggedInUser);
        return "posts.html";
    }

    @GetMapping("/addPost")
    public String getAddPostsForm(){
        return "addPosts.html";
    }

    @PostMapping("/addPost")
    public RedirectView addPost(String body, String category, String type, Integer weight, String status, Principal p){
        UserApplication currentUser = userApplicationRepository.findByUsername(p.getName());
        Post newPost=new Post(body,category,type,weight,true,currentUser);
        postRepository.save(newPost);
        return new RedirectView("/myprofile");
    }

    @PutMapping("/edit-post/{id}")
    public RedirectView editPost(@PathVariable long id, String body, String category, String type, Integer weight, String status, Principal p){
        UserApplication currentUser = userApplicationRepository.findByUsername(p.getName());
        Post postToEdit = postRepository.findById(id).get();
        postToEdit.setBody(body);
        postToEdit.setCategory(category);
        postToEdit.setType(type);
        postToEdit.setWeight(weight);
        postToEdit.setAvailable(status.equals("true"));
        postRepository.save(postToEdit);
        return new RedirectView("/myprofile");
    }

    @DeleteMapping("/delete-post/{id}")
    public RedirectView deletePost(@PathVariable(value = "id") long id){
        postRepository.deleteById(id);
        return new RedirectView("/myprofile");
    }

}
