package com.kkk.nordeaassignment.ui.activity;

import android.location.Location;

/**
 * @author Kamil Popławski <Kamil.Poplawski88@gmail.com> on 2016-03-18.
 */
public interface MainView {

    void showError(String errorMessage);

    void showLatLng(Location location);
}