package com.kkk.nordeaassignment.services;

import java.util.List;

/**
 * @author Kamil Popławski <Kamil.Poplawski88@gmail.com> on 2016-03-17.
 */
public class FoursquareSearchResponse {

    private Response response;

    public class Response {
        private List<Venue> venues;

        public List<Venue> getVenues() {
            return venues;
        }
    }

    public class Venue {
        private String name;
        private Location location;

        public String getName() {
            return name;
        }

        public Location getLocation() {
            return location;
        }
    }

    public class Location {
        private String address;
        private int distance;

        public String getAddress() {
            return address;
        }

        public int getDistance() {
            return distance;
        }
    }

    public Response getResponse() {
        return response;
    }
}
