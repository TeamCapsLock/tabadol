package com.example.tabadol.restControllers;

import com.example.tabadol.JsonClasses.AddPostFormJson;
import com.example.tabadol.JsonClasses.ResponseJson;
import com.example.tabadol.contoller.Views;
import com.example.tabadol.model.Post;
import com.example.tabadol.model.UserApplication;
import com.example.tabadol.repository.PostRepository;
import com.example.tabadol.repository.UserApplicationRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RestPostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserApplicationRepository userApplicationRepository;


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



    @PostMapping("/jaddPost")
    public ResponseEntity addPost_j(@RequestBody AddPostFormJson fields, Principal p){

        if(fields.getBody().length() == 0)
            return new ResponseEntity(new ResponseJson("Post can't be empty!"), HttpStatus.NOT_ACCEPTABLE);

        UserApplication currentUser = userApplicationRepository.findByUsername(p.getName());
        Post newPost=new Post(fields.getBody(), fields.getCategory(), fields.getType(), fields.getWeight(),true,currentUser);
        postRepository.save(newPost);
        return new ResponseEntity(new ResponseJson("Post added successfully.."), HttpStatus.OK);
    }

//    @PostMapping("/jedit-post/{id}")
//    public RedirectView editPost_j(@PathVariable long id, String body, String category, String type, Integer weight, String status, Principal p){
//        UserApplication currentUser = userApplicationRepository.findByUsername(p.getName());
//        Post postToEdit = postRepository.findById(id).get();
//        postToEdit.setBody(body);
//        postToEdit.setCategory(category);
//        postToEdit.setType(type);
//        postToEdit.setWeight(weight);
//        postToEdit.setAvailable(status.equals("true"));
//        postRepository.save(postToEdit);
//        return new RedirectView("/myprofile");
//    }

    @DeleteMapping("/jdelete-post/{id}")
    public ResponseEntity deletePost_j(@PathVariable(value = "id") long id){

        try {
            postRepository.deleteById(id);
            return new ResponseEntity(new ResponseJson("Post deleted Successfully.."), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity(new ResponseJson("Post Not found"), HttpStatus.NOT_FOUND);
        }
    }

}
