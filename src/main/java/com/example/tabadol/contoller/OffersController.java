package com.example.tabadol.contoller;

import com.example.tabadol.model.Post;
import com.example.tabadol.model.UserApplication;
import com.example.tabadol.repository.PostRepository;
import com.example.tabadol.repository.UserApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

@Controller
public class OffersController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserApplicationRepository userApplicationRepository;

    @PostMapping("/makeOffer/{id}")
    public RedirectView makeOffer(@PathVariable long id, String body, String category, String type, Integer weight, String status, Principal p){
        UserApplication currentUser = userApplicationRepository.findByUsername(p.getName());
        Post sourcePost = new Post(body,category,type,weight,true,currentUser);
        sourcePost.setOfferType("Private");
        Post postToMakeOffer = postRepository.findById(id).get();
        sourcePost.makeOffer(postToMakeOffer);
        postRepository.save(sourcePost);
        return new RedirectView("/posts");
    }

    @GetMapping("/receivedoffers")
    public String getMyOffersPage(Principal p, Model m){

        return "myOffers";
    }

    @GetMapping("/sentoffers")
    public String getMySentOffersPage(Principal p, Model m){
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
        List<Post> posts = postRepository.findAllByUser_id(loggedInUser.getId());
        for( Post post : posts){
            System.out.println("--------------");
            System.out.println("--------------");
            System.out.println("--------------");
            System.out.println(post.getOffers());
        }

        m.addAttribute("posts", posts);
        return "sentOffers";
    }

}
