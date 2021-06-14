package com.example.tabadol.JsonClasses;

public class AcceptOfferFormJson {

    long source_id;
    long destination_id;

    public AcceptOfferFormJson(long source_id, long destination_id) {
        this.source_id = source_id;
        this.destination_id = destination_id;
    }

    public AcceptOfferFormJson() {
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
}
