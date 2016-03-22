package com.kkk.nordeaassignment.services;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * @author Kamil Popławski <Kamil.Poplawski88@gmail.com> on 2016-03-17.
 */
public interface Foursquare {

    @GET("/v2/venues/search?")
    Call<FoursquareSearchResponse> getData(@Query("ll") String latitudeLongitude, @Query("client_id") String clientId, @Query("client_secret") String clientSecret, @Query("v") String version);
}
