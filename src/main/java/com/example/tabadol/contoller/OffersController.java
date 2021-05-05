package com.example.tabadol.contoller;

import com.example.tabadol.model.Offer;
import com.example.tabadol.model.Post;
import com.example.tabadol.model.UserApplication;
import com.example.tabadol.repository.OfferRepository;
import com.example.tabadol.repository.PostRepository;
import com.example.tabadol.repository.UserApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class OffersController {

    @Autowired
    PostRepository postRepository;
    @Autowired
    OfferRepository offerRepository;
    @Autowired
    UserApplicationRepository userApplicationRepository;

    @PostMapping("/makeOffer/{id}")
    public RedirectView makeOffer(@PathVariable long id, String body, String category, String type, Integer weight, String status, Principal p){
        UserApplication currentUser = userApplicationRepository.findByUsername(p.getName());
        Post newPost=new Post(body,category,type,weight,true,currentUser);
        newPost.setOfferType("Private");
        Post savedPost =postRepository.save(newPost);
        Offer newOffer=new Offer(savedPost.getId(),id);
        offerRepository.save(newOffer);
        return new RedirectView("/");

    }

}
