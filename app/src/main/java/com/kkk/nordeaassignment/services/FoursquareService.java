package com.kkk.nordeaassignment.services;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * @author Kamil Popławski <Kamil.Poplawski88@gmail.com> on 2016-03-17.
 */
public class FoursquareService {

    private static final String API_URL = "https://api.foursquare.com";

    public static Foursquare getService() {
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Foursquare.class);
    }

}