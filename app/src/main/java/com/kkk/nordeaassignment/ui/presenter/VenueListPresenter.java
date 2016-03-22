package com.kkk.nordeaassignment.ui.presenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kkk.nordeaassignment.services.AuthData;
import com.kkk.nordeaassignment.services.Foursquare;
import com.kkk.nordeaassignment.services.FoursquareSearchResponse;
import com.kkk.nordeaassignment.services.FoursquareService;
import com.kkk.nordeaassignment.ui.activity.MainView;
import com.kkk.nordeaassignment.ui.fragment.SearchVenuesView;
import com.kkk.nordeaassignment.ui.model.VenueModel;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * @author Kamil Popławski <Kamil.Poplawski88@gmail.com> on 2016-03-18.
 */
public class VenueListPresenter implements Presenter, Callback<FoursquareSearchResponse> {
    private static final String TAG = "VenueListPresenter";
    final public static int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private Call<FoursquareSearchResponse> call;
    private MainView mainView;
    private SearchVenuesView searchVenuesView;
    private Foursquare foursquare;

    @Override
    public void attachView(MainView view, SearchVenuesView searchVenuesView) {
        this.mainView = view;
        this.searchVenuesView = searchVenuesView;
    }

    @Override
    public void onStop() {
        if (call != null)
            call.cancel();
    }

    public void getVenues() {
        Location location = getLocation();

        if (location == null) return;

        mainView.showLatLng(location);
        call = getFoursquare().getData(getLatLongFormatted(location), AuthData.CLIENT_ID, AuthData.CLIENT_SECRET, AuthData.VERSION);
        call.enqueue(this);
    }


    private String getLatLongFormatted(Location location) {
        return String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude());
    }

    @Override
    public void onResponse(Response<FoursquareSearchResponse> response, Retrofit retrofit) {
        List<VenueModel> mVenueModel = new ArrayList<>();
        for (FoursquareSearchResponse.Venue venue : response.body().getResponse().getVenues()) {
            mVenueModel.add(new VenueModel(venue.getName(), venue.getLocation().getAddress(), venue.getLocation().getDistance()));
        }

        searchVenuesView.showVenues(mVenueModel);
    }

    @Override
    public void onFailure(Throwable t) {
        mainView.showError(t.getLocalizedMessage());
    }


    /**
     * @return last known device location
     */
    public Location getLocation() {
        LocationManager locationManager = (LocationManager) ((AppCompatActivity) mainView).getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);

        Log.d(TAG, "Provider " + provider + " has been selected.");

        if (ActivityCompat.checkSelfPermission((AppCompatActivity) mainView, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission((AppCompatActivity) mainView, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askForPermissions();
            return null;
        }
        return locationManager.getLastKnownLocation(provider);
    }

    private void askForPermissions() {
        ActivityCompat.requestPermissions((AppCompatActivity) mainView,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
    }


    public Foursquare getFoursquare() {
        if (foursquare == null)
            foursquare = FoursquareService.getService();
        return foursquare;
    }

    public void setFoursquare(Foursquare foursquare) {
        this.foursquare = foursquare;
    }
}
