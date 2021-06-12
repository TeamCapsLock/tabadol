package com.example.tabadol.JsonClasses;

import com.example.tabadol.contoller.Views;
import com.example.tabadol.model.Post;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.Set;

public class ReceivedOffersJson {

    @JsonView(Views.Public.class)
    private Post postHasReceivedTheOffers ;

    @JsonView(Views.Public.class)
    private Set<Post> theReceivedOffersForOnePost;

    public ReceivedOffersJson(Post postHasReceivedTheOffers, Set<Post> theReceivedOffersForOnePost) {
        this.postHasReceivedTheOffers = postHasReceivedTheOffers;
        this.theReceivedOffersForOnePost = theReceivedOffersForOnePost;
    }

    public ReceivedOffersJson() {
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
}
