package com.example.tabadol.JsonClasses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupFormJson {
    String username;
    String email;
    String firstname;
    String lastname;
    String password;
    String confirm;
    String skills;
    String bio;
    String phone;
    String image;

    public SignupFormJson(String username, String email, String firstname, String lastname, String password, String confirm, String skills, String bio, String phone, String image) {
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.confirm = confirm;
        this.skills = skills;
        this.bio = bio;
        this.phone = phone;
        this.image = image;
    }


    public SignupFormJson() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
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

    public boolean isAllFieldsEntered(){
        if(username == null || email == null || firstname == null ||
           lastname == null || password == null || confirm == null ||
           skills == null || bio == null || phone == null || image == null )
            return false;

        return true;

    }

    public boolean isValidPhone(){
        Pattern pattern = Pattern.compile("^07[789]\\d{7}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.find();
    }

    public boolean isValidEmail(){
        Pattern pattern = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }
}
