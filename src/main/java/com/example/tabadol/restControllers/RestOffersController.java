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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RestOffersController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserApplicationRepository userApplicationRepository;

//    @PostMapping("/jmakeOffer/{id}")
//    public RedirectView makeOffer_j(@PathVariable long id, String body, String category, String type, Integer weight, String status, Principal p){
//        UserApplication currentUser = userApplicationRepository.findByUsername(p.getName());
//        Post sourcePost = new Post(body,category,type,weight,true,currentUser);
//        sourcePost.setOfferType("Private");
//        Post postToMakeOffer = postRepository.findById(id).get();
//        sourcePost.makeOffer(postToMakeOffer);
//        Post savedSourcePost = postRepository.save(sourcePost);
//        postToMakeOffer.receiveOffer(savedSourcePost);
//        postRepository.save(postToMakeOffer);
//        return new RedirectView("/posts");
//    }


    @GetMapping("/jreceivedoffers")
    @JsonView(Views.Public.class)
    @ResponseBody
    public List<ReceivedOffersJson> getMyOffersPage_j(Principal p, Model m){
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
        List<Post> posts = postRepository.findAllByUser_id(loggedInUser.getId());
        List<ReceivedOffersJson> offers = new ArrayList<>();

        for(Post post : posts){
            if(post.getReceivedOffers().isEmpty() || ! post.isAvailable())
                continue;
            ReceivedOffersJson receivedOffersJson = new ReceivedOffersJson();
            receivedOffersJson.setPostHasReceivedTheOffers(post);
            receivedOffersJson.setTheReceivedOffersForOnePost(post.getReceivedOffers());
            offers.add(receivedOffersJson);

        }

        return offers;
    }



    @GetMapping("/jsentoffers")
    @ResponseBody
    @JsonView(Views.Public.class)
    public List<SentOffersJson> getMySentOffersPage_j(Principal p, Model m){
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
        List<Post> posts = postRepository.findAllByUser_id(loggedInUser.getId());


//        List<List<Post>> listToCheck = posts.stream().map(p1 -> p1.getOffers().stream().filter(p3 -> p3.isAvailable()).collect(Collectors.toList())).collect( Collectors.toList());
//        listToCheck= listToCheck.stream().filter(pp-> ! pp.isEmpty()).collect( Collectors.toList());
//
//        m.addAttribute("listToCheck", listToCheck);
//        m.addAttribute("posts", posts);
//        m.addAttribute("loggedInUser", loggedInUser);

        List<SentOffersJson> offers = new ArrayList<>();
        for(Post post : posts){
            if(post.getOffers().isEmpty() || ! post.isAvailable())
                continue;
            SentOffersJson sentOffersJson = new SentOffersJson();
            sentOffersJson.setPostThatSentTheOffers(post);
            sentOffersJson.setPostsThatReceivedTheSentOffer(post.getOffers());
            offers.add(sentOffersJson);
        }
        return offers;
    }



    @GetMapping("/jacceptedoffers")
    @ResponseBody
    @JsonView(Views.Public.class)
    public List<AcceptedOffersJson> getAcceptedOffersPage_j(Principal p, Model m){
        UserApplication loggedInUser = userApplicationRepository.findByUsername(p.getName());
        List<Post> posts = postRepository.findAllByUser_id(loggedInUser.getId());

        List<AcceptedOffersJson> offers = new ArrayList<>();

        // to add sent offers:
        for(Post post : posts){
            if(post.getOffers().isEmpty() || post.isAvailable())
                continue;

            AcceptedOffersJson offer = new AcceptedOffersJson();
            offer.setPostThatSentTheOffers(post);
            offer.setPostsThatReceivedTheSentOffer(post.getOffers());
            offers.add(offer);
        }

        // to add received offers:
        for(Post post : posts){
            if(post.getReceivedOffers().isEmpty() || post.isAvailable())
                continue;

            AcceptedOffersJson offer = new AcceptedOffersJson();
            offer.setPostHasReceivedTheOffers(post);
            offer.setTheReceivedOffersForOnePost(post.getReceivedOffers());
            offers.add(offer);
        }

        return offers;
    }





    @PostMapping("/jexchange/{id}")
    public ResponseEntity exchangeOffers_j (@PathVariable long id, Principal p, @RequestBody MyPostIdJson myPostId){

        UserApplication currentUser = userApplicationRepository.findByUsername(p.getName());
        Post sourcePost = postRepository.findById(myPostId.getMyPostId()).get();
        Post postToMakeOffer = postRepository.findById(id).get();

        sourcePost.makeOffer(postToMakeOffer);
        postToMakeOffer.receiveOffer(sourcePost);
        postRepository.save(sourcePost);
        postRepository.save(postToMakeOffer);

        return new ResponseEntity(new ResponseJson("exchange requeste is done.."), HttpStatus.OK);

    }

    @PostMapping("/jacceptoffer")
    public ResponseEntity acceptTheOffer_j(@RequestBody AcceptOfferFormJson fields){
        Post sourcePost = postRepository.findById(fields.getSource_id()).get();
        Post destinationPost = postRepository.findById(fields.getDestination_id()).get();

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


        // return to new page .. that holds contact info..
        return new ResponseEntity(new ResponseJson("Posts are exchanched successfully"), HttpStatus.OK);
    }



    @PostMapping("/jdeclineoffer")
    public ResponseEntity declineTheOffer_j(@RequestBody AcceptOfferFormJson fields){
        Post sourcePost = postRepository.findById(fields.getSource_id()).get();
        Post destinationPost = postRepository.findById(fields.getDestination_id()).get();
        sourcePost.deleteOffer(destinationPost);
        postRepository.save(sourcePost);

        // return to new page .. that holds contact info..
        return new ResponseEntity(new ResponseJson("You've declined the offer.."), HttpStatus.OK);
    }


}
