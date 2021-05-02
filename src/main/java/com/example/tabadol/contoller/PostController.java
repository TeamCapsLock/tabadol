package com.example.tabadol.contoller;

import com.example.tabadol.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {

    @Autowired
    PostRepository postRepository;


    @GetMapping("/posts")
    public String getPosts(Model m){
        m.addAttribute("posts",postRepository.findAll());
        return "posts.html";
    }

}
