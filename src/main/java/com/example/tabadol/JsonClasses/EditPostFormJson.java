package com.example.tabadol.JsonClasses;

public class EditPostFormJson {
    private String firstname;
    private String lastname;
    private String skills;
    private String bio;
    private String phone;
    private String image;

    public EditPostFormJson(String firstname, String lastname, String skills, String bio, String phone, String image) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.skills = skills;
        this.bio = bio;
        this.phone = phone;
        this.image = image;
    }

    public EditPostFormJson() {
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
