package com.example.tabadol.JsonClasses;

import com.example.tabadol.contoller.Views;
import com.example.tabadol.model.Post;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.Set;

public class SentOffersJson {


    @JsonView(Views.Public.class)
    private Post postThatSentTheOffers ;

    @JsonView(Views.Public.class)
    private Set<Post> postsThatReceivedTheSentOffer;

    public SentOffersJson(Post postThatSentTheOffers, Set<Post> postsThatReceivedTheSentOffer) {
        this.postThatSentTheOffers = postThatSentTheOffers;
        this.postsThatReceivedTheSentOffer = postsThatReceivedTheSentOffer;
    }

    public SentOffersJson() {
    }

    public Post getPostThatSentTheOffers() {
        return postThatSentTheOffers;
    }

    public void setPostThatSentTheOffers(Post postThatSentTheOffers) {
        this.postThatSentTheOffers = postThatSentTheOffers;
    }

    public Set<Post> getPostsThatReceivedTheSentOffer() {
        return postsThatReceivedTheSentOffer;
    }

    public void setPostsThatReceivedTheSentOffer(Set<Post> postsThatReceivedTheSentOffer) {
        this.postsThatReceivedTheSentOffer = postsThatReceivedTheSentOffer;
    }
}
