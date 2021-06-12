package com.example.tabadol.JsonClasses;

import com.example.tabadol.contoller.Views;
import com.example.tabadol.model.Post;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

public class UserJson {

    @JsonView(Views.ProfileView.class)
    private long id;

    @JsonView(Views.ProfileView.class)
    private String username;

    @JsonView(Views.ProfileView.class)
    private String email;

    @JsonView(Views.ProfileView.class)
    private String firstname;

    @JsonView(Views.ProfileView.class)
    private String lastname;

    @JsonView(Views.ProfileView.class)
    private String skills;

    @JsonView(Views.ProfileView.class)
    private String bio;

    @JsonView(Views.ProfileView.class)
    private int numberOfFollowers = 0;
    // Added

    @JsonView(Views.ProfileView.class)
    private int numberOfFollowing = 0;

    @JsonView(Views.ProfileView.class)
    private double rating = 5;

    @JsonView(Views.ProfileView.class)
    private String phone;

    @JsonView(Views.ProfileView.class)
    private String image;

    @JsonView(Views.ProfileView.class)
    private List<Post> posts = null;




    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public UserJson(long id, String username, String email, String firstname, String lastname, String skills, String bio, int numberOfFollowers, int numberOfFollowing, double rating, String phone, String image, List<Post> posts) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.skills = skills;
        this.bio = bio;
        this.numberOfFollowers = numberOfFollowers;
        this.numberOfFollowing = numberOfFollowing;
        this.rating = rating;
        this.phone = phone;
        this.image = image;
        this.posts = posts;
    }

    public UserJson(long id, String username, String email, String firstname, String lastname, String skills, String bio, int numberOfFollowers, int numberOfFollowing, double rating, String phone, String image) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.skills = skills;
        this.bio = bio;
        this.numberOfFollowers = numberOfFollowers;
        this.numberOfFollowing = numberOfFollowing;
        this.rating = rating;
        this.phone = phone;
        this.image = image;
    }

    public UserJson() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getNumberOfFollowers() {
        return numberOfFollowers;
    }

    public void setNumberOfFollowers(int numberOfFollowers) {
        this.numberOfFollowers = numberOfFollowers;
    }

    public int getNumberOfFollowing() {
        return numberOfFollowing;
    }

    public void setNumberOfFollowing(int numberOfFollowing) {
        this.numberOfFollowing = numberOfFollowing;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
