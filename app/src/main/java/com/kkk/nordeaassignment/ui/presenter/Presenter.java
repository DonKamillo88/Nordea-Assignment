package com.kkk.nordeaassignment.ui.presenter;


import com.kkk.nordeaassignment.ui.activity.MainView;
import com.kkk.nordeaassignment.ui.fragment.SearchVenuesView;

/**
 * @author Kamil Popławski <Kamil.Poplawski88@gmail.com> on 2016-03-18.
 */
public interface Presenter {

    void attachView(MainView mainView, SearchVenuesView searchVenuesView);

    void onStop();
}