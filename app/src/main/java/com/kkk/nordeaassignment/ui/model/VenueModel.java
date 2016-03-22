package com.kkk.nordeaassignment.ui.model;

/**
 * @author Kamil Popławski <Kamil.Poplawski88@gmail.com> on 2016-03-17.
 */
public class VenueModel {

    private String name;
    private String address;
    private int distance;


    public VenueModel(String name, String address, int distance) {
        this.name = name;
        this.address = address;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getDistance() {
        return distance;
    }
}
