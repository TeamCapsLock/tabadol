package com.example.tabadol.model;

import com.example.tabadol.contoller.Views;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

@Entity
//@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({Views.Public.class, Views.ProfileView.class})
    private long id;

    @JsonView({Views.Public.class, Views.ProfileView.class})
    private String body;

    @JsonView({Views.Public.class, Views.ProfileView.class})
    private String category;

    @JsonView({Views.Public.class, Views.ProfileView.class})
    private String type;

    @JsonView({Views.Public.class, Views.ProfileView.class})
    private Integer weight;

    @JsonView({Views.Public.class, Views.ProfileView.class})
    private Boolean available;

    @JsonView({Views.Public.class, Views.ProfileView.class})
    private String createdAt;

    @JsonView({Views.Public.class, Views.ProfileView.class})
    private String offerType="general";


    @ManyToOne
    @JsonView(Views.Public.class)
    UserApplication user;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "offers",
            joinColumns =  {
                    @JoinColumn(name ="source_id")},
            inverseJoinColumns = {
                    @JoinColumn(name ="destination_id")
            })
    Set<Post> offers = new HashSet<>();


    @ManyToMany(mappedBy="offers", cascade = CascadeType.ALL)
    Set<Post> receivedOffers = new HashSet<>();








//    @OneToMany(mappedBy = "post",cascade =CascadeType.ALL)
//    Set<Post> receivedOffers = new HashSet<>();



//    @OneToMany(mappedBy = "source_post_id",cascade=CascadeType.ALL)
//    Set<Offer> sources = new HashSet<>();
//
//    @OneToMany(mappedBy = "destination_post_id",cascade=CascadeType.ALL)
//    Set<Offer> destinations = new HashSet<>();


    public Set<Post> getOffers() {
        return offers;
    }



    public Post(){}
    public Post(String body, String category, String type, Integer weight, Boolean available, UserApplication user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm");
        this.createdAt = sdf.format(new Timestamp(System.currentTimeMillis()).getTime());
        this.body = body;
        this.category = category;
        this.type = type;
        this.weight = weight;
        this.available = true;
        this.user=user;
    }

    public void makeOffer(Post postToMakeOffer){
        offers.add(postToMakeOffer);
    }

    public void receiveOffer(Post offerToReceive){
        receivedOffers.add(offerToReceive);
    }

    public Set<Post> getReceivedOffers() {
        return receivedOffers;
    }

    public void deleteOffer(Post postToDelete){
        if(offers.contains(postToDelete))
            offers.remove(postToDelete);
    }

    public void deleteReceivedOffer(Post postToDelete){
        if(receivedOffers.contains(postToDelete))
            receivedOffers.remove(postToDelete);
    }

    public void deleteAllOffers(){
       offers.clear();
    }

    public void deleteAllReceivedOffers(){
        receivedOffers.clear();
    }




    public boolean isThereOfferOf(Post postToCheck){
        return offers.contains(postToCheck);
    }

    public long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Boolean isAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public UserApplication getUser() {
        return user;
    }

    public void setUser(UserApplication user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }
}
