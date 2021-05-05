package com.example.tabadol.model;

import javax.persistence.*;

@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
//    @ManyToOne(cascade =CascadeType.ALL,targetEntity = Post.class)
    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name = "source_id")
    private long source_id;
//    @ManyToOne(cascade =CascadeType.ALL,targetEntity = Post.class)
    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name = "destination_id")
    private long destination_id;
    private String status="Pending";

    public long getId() {
        return id;
    }

    public Offer(long source_id, long destination_id) {
        this.source_id = source_id;
        this.destination_id = destination_id;
    }

    public Offer(){

    }

    public long getSource_id() {
        return source_id;
    }

    public void setSource_id(long source_id) {
        this.source_id = source_id;
    }

    public long getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(long destination_id) {
        this.destination_id = destination_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
