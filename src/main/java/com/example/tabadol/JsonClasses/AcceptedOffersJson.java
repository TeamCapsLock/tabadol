package com.example.tabadol.JsonClasses;

import com.example.tabadol.contoller.Views;
import com.example.tabadol.model.Post;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.Set;

public class AcceptedOffersJson {

    @JsonView(Views.Public.class)
    private Post postHasReceivedTheOffers ;

    @JsonView(Views.Public.class)
    private Set<Post> theReceivedOffersForOnePost;

    @JsonView(Views.Public.class)
    private Post postThatSentTheOffers ;

    @JsonView(Views.Public.class)
    private Set<Post> postsThatReceivedTheSentOffer;


    public AcceptedOffersJson(Post postHasReceivedTheOffers, Set<Post> theReceivedOffersForOnePost, Post postThatSentTheOffers, Set<Post> postsThatReceivedTheSentOffer) {
        this.postHasReceivedTheOffers = postHasReceivedTheOffers;
        this.theReceivedOffersForOnePost = theReceivedOffersForOnePost;
        this.postThatSentTheOffers = postThatSentTheOffers;
        this.postsThatReceivedTheSentOffer = postsThatReceivedTheSentOffer;
    }

    public AcceptedOffersJson() {
    }

    public Post getPostHasReceivedTheOffers() {
        return postHasReceivedTheOffers;
    }

    public void setPostHasReceivedTheOffers(Post postHasReceivedTheOffers) {
        this.postHasReceivedTheOffers = postHasReceivedTheOffers;
    }

    public Set<Post> getTheReceivedOffersForOnePost() {
        return theReceivedOffersForOnePost;
    }

    public void setTheReceivedOffersForOnePost(Set<Post> theReceivedOffersForOnePost) {
        this.theReceivedOffersForOnePost = theReceivedOffersForOnePost;
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
