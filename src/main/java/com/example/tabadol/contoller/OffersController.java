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
        Post savedSourcePost = postRepository.save(sourcePost);
        postToMakeOffer.receiveOffer(savedSourcePost);
        postRepository.save(postToMakeOffer);
        return new RedirectView("/posts");
    }

    @GetMapping("/receivedoffers")
    public String getMyOffersPage(Principal p, Model m){
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
        List<Post> posts = postRepository.findAllByUser_id(loggedInUser.getId());
        m.addAttribute("posts", posts);

        return "myOffers";
    }

    @GetMapping("/sentoffers")
    public String getMySentOffersPage(Principal p, Model m){
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
        List<Post> posts = postRepository.findAllByUser_id(loggedInUser.getId());
        m.addAttribute("posts", posts);
        return "sentOffers";
    }

    @GetMapping("/acceptedoffers")
    public String getAcceptedOffersPage(Principal p, Model m){
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
        List<Post> posts = postRepository.findAllByUser_id(loggedInUser.getId());
        m.addAttribute("posts", posts);



        return "acceptedOffers";
    }

    @PostMapping("/exchange/{id}")
    public  RedirectView exchangeOffers (@PathVariable long id, Model m, Principal p, long exhcangethispost){
        UserApplication currentUser = userApplicationRepository.findByUsername(p.getName());
        Post sourcePost = postRepository.findById(exhcangethispost).get();
        Post postToMakeOffer = postRepository.findById(id).get();

        sourcePost.makeOffer(postToMakeOffer);
        postToMakeOffer.receiveOffer(sourcePost);
        postRepository.save(sourcePost);
        postRepository.save(postToMakeOffer);

        return new RedirectView("/posts");

    }

    @PostMapping("/acceptoffer")
    public RedirectView acceptTheOffer(long source_id, long destination_id){
        Post sourcePost = postRepository.findById(source_id).get();
        Post destinationPost = postRepository.findById(destination_id).get();

        sourcePost.deleteAllOffers();
        sourcePost.deleteAllReceivedOffers();

        for (Post post : destinationPost.getReceivedOffers()){
             post.deleteOffer(destinationPost);
        }

        destinationPost.deleteAllOffers();
        destinationPost.deleteAllReceivedOffers();

        sourcePost.setAvailable(false);
        destinationPost.setAvailable(false);

        sourcePost.makeOffer(destinationPost);
        destinationPost.receiveOffer(sourcePost);

        postRepository.save(sourcePost);
        postRepository.save(destinationPost);






        System.out.println("-------------");
        System.out.println("-------------");
        System.out.println("-------------");
        System.out.println("source id : "+ source_id);
        System.out.println("destination id : "+ destination_id);
        System.out.println("------------------");
        System.out.println("------------------");
        System.out.println("received offers by destination id");
        for( Post post : destinationPost.getReceivedOffers() )
            System.out.println( post.getId() + "\t" + post.getBody());
        System.out.println("------------------");
        System.out.println("------------------");
        System.out.println("sent offers by source id");
        for( Post post : sourcePost.getOffers() )
            System.out.println( post.getId() + "\t" + post.getBody());



        // return to new page .. that holds contact info..
        return new RedirectView("/receivedoffers");
    }



    @PostMapping("/declineoffer")
    public RedirectView declineTheOffer(long source_id, long destination_id){
        Post sourcePost = postRepository.findById(source_id).get();
        Post destinationPost = postRepository.findById(destination_id).get();
        sourcePost.deleteOffer(destinationPost);
        postRepository.save(sourcePost);

        // return to new page .. that holds contact info..
        return new RedirectView("/receivedoffers");
    }


}
