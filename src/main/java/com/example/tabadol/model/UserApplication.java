package com.example.tabadol.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity(name = "users")
public class UserApplication implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private String skills;
    private String bio;
    private int numberOfFollowers =0;



    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "followers",
    joinColumns =  {
            @JoinColumn(name ="user_id")},
            inverseJoinColumns = {
            @JoinColumn(name ="following_user")


    })
    Set<UserApplication> users_I_follow = new HashSet<>();


    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    List<Post> posts = new ArrayList<>();

    public UserApplication() {
    }

    public UserApplication(String username,String email, String firstname, String lastname, String password, String skills, String bio) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.skills = skills;
        this.bio = bio;
        this.email = email;
    }

    public Set<UserApplication> getUsers_I_follow() {
        return users_I_follow;
    }
    public void followUser (UserApplication userToFollow){
            users_I_follow.add(userToFollow);
    }

    public boolean isFollowingUser(UserApplication user){
        return users_I_follow.contains(user);
    }

    public int numberOfPeopleIfollow(){
        return users_I_follow.size();
    }



    public void unfollowUser (UserApplication userTounFollow){
        if(!users_I_follow.isEmpty()){
            users_I_follow.remove(userTounFollow);
        }
    }






    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }


    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public void increaseNumberOfFollowers() {
        this.numberOfFollowers++;
    }

    public void decreaseNumberOfFollowers() {
        this.numberOfFollowers--;
    }
}
