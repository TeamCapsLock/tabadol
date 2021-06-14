package com.example.tabadol.JsonClasses;

public class AddPostFormJson {
    String body;
    String category;
    String type;
    Integer weight;

    public AddPostFormJson(String body, String category, String type, Integer weight) {
        this.body = body;
        this.category = category;
        this.type = type;
        this.weight = weight;
    }

    public AddPostFormJson() {
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

}
