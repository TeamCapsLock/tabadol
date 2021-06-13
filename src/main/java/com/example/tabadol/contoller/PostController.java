package com.example.tabadol.contoller;

import com.example.tabadol.model.Post;
import com.example.tabadol.model.UserApplication;
import com.example.tabadol.repository.PostRepository;
import com.example.tabadol.repository.UserApplicationRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserApplicationRepository userApplicationRepository;


    @GetMapping("/posts")
    public String getPosts(Model m, Principal p){
        UserApplication loggedInUser = null;
        try{
            loggedInUser = userApplicationRepository.findByUsername(p.getName());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        m.addAttribute("posts",postRepository.findAll());
        m.addAttribute("loggedInUser",loggedInUser);
        return "posts.html";
    }


    @GetMapping("/jposts")
    @ResponseBody
    @JsonView(Views.Public.class)
    public List<Post> getPosts_j(Model m, Principal p){
        UserApplication loggedInUser = null;
        try{
            loggedInUser = userApplicationRepository.findByUsername(p.getName());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        List<Post> posts2 = postRepository.findAll();
        posts2 = posts2.stream().filter(post -> (post.isAvailable() && !post.getUser().getUsername().equals(p.getName()))).collect(Collectors.toList());
//        m.addAttribute("posts",postRepository.findAll());
//        m.addAttribute("loggedInUser",loggedInUser);
        return posts2;
    }


    //no need to return as JSON
    @GetMapping("/addPost")
    public String getAddPostsForm(Principal p, Model m){
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
        m.addAttribute("loggedInUser",loggedInUser);
        return "addPosts.html";
    }

    @PostMapping("/addPost")
    public RedirectView addPost(String body, String category, String type, Integer weight, String status, Principal p){
        UserApplication currentUser = userApplicationRepository.findByUsername(p.getName());
        Post newPost=new Post(body,category,type,weight,true,currentUser);
        postRepository.save(newPost);
        return new RedirectView("/myprofile");
    }

    @PostMapping("/edit-post/{id}")
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

    @PostMapping("/delete-post/{id}")
    public RedirectView deletePost(@PathVariable(value = "id") long id){
        postRepository.deleteById(id);
        return new RedirectView("/myprofile");
    }

}
