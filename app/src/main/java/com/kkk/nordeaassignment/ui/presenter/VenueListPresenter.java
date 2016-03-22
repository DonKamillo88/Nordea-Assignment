package com.kkk.nordeaassignment.ui.presenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
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
    final private static long MIN_TIME_BW_UPDATES = 0;
    final private static float MIN_DISTANCE_CHANGE_FOR_UPDATES = 10000;

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

        if (location != null)
            callForVenues(location);
    }

    private void callForVenues(Location location) {
        mainView.showLatLng(location);
        call = getFoursquare().getData(getLatLongFormatted(location), AuthData.CLIENT_ID, AuthData.CLIENT_SECRET, AuthData.VERSION);
        call.enqueue(this);
    }

    private String getLatLongFormatted(Location location) {
        return String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude());
    }

    @Override
    public void onResponse(Response<FoursquareSearchResponse> response, Retrofit retrofit) {
        List<VenueModel> venueModels = new ArrayList<>();
        for (FoursquareSearchResponse.Venue venue : response.body().getResponse().getVenues()) {
            venueModels.add(new VenueModel(venue.getName(), venue.getLocation().getAddress(), venue.getLocation().getDistance()));
        }

        searchVenuesView.showVenues(venueModels);
    }

    public void permissionsResult(boolean result) {
        if (result) {
            getVenues();
        } else {
            searchVenuesView.showPermissionsInfo();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        mainView.showError(t.getLocalizedMessage());
    }

    /**
     * Get last known device location, or when it is not available request location updates
     */
    public Location getLocation() {
        if (ActivityCompat.checkSelfPermission((AppCompatActivity) mainView, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission((AppCompatActivity) mainView, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askForPermissions();
            return null;
        }

        LocationManager locationManager = (LocationManager) ((AppCompatActivity) mainView).getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Log.d(TAG, "Provider " + provider + " has been selected.");

        Location lastKnownLocation = locationManager.getLastKnownLocation(provider);
        if (lastKnownLocation == null)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);

        return null;
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "onLocationChanged - lat:" + location.getLatitude() + ", lng:" + location.getLongitude());
            callForVenues(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    private void askForPermissions() {
        ActivityCompat.requestPermissions((AppCompatActivity) mainView,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET},
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
