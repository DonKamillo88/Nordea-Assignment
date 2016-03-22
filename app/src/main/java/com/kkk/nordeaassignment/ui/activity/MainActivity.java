package com.kkk.nordeaassignment.ui.activity;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.kkk.nordeaassignment.R;
import com.kkk.nordeaassignment.ui.fragment.SearchVenuesFragment;
import com.kkk.nordeaassignment.ui.presenter.VenueListPresenter;

/**
 * @author Kamil Popławski <Kamil.Poplawski88@gmail.com> on 2016-03-17.
 */
public class MainActivity extends AppCompatActivity implements MainView {

    private VenueListPresenter venueListPresenter;
    private TextView latField;
    private TextView lngField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);

        latField = (TextView) findViewById(R.id.lat);
        lngField = (TextView) findViewById(R.id.lng);

        initializePresenter();

        venueListPresenter.getVenues();
    }

    @Override
    protected void onStop() {
        super.onStop();
        venueListPresenter.onStop();
    }

    private void initializePresenter() {
        venueListPresenter = new VenueListPresenter();
        FragmentManager fm = getSupportFragmentManager();
        SearchVenuesFragment searchVenuesFragment = (SearchVenuesFragment) fm.findFragmentById(R.id.search_venues_fragment);

        venueListPresenter.attachView(this, searchVenuesFragment);
    }


    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        latField.setText(getResources().getText(R.string.unknown));
        lngField.setText(getResources().getText(R.string.unknown));
    }

    @Override
    public void showLatLng(Location location) {
        if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            latField.setText(String.valueOf(lat));
            lngField.setText(String.valueOf(lng));
        } else {
            latField.setText(getResources().getText(R.string.unknown));
            lngField.setText(getResources().getText(R.string.unknown));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case VenueListPresenter.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                venueListPresenter.permissionsResult(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED);
            }
        }
    }
}
