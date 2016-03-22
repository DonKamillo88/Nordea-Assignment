package com.kkk.nordeaassignment.ui.fragment;

import com.kkk.nordeaassignment.ui.model.VenueModel;

import java.util.List;

/**
 * @author Kamil Popławski <Kamil.Poplawski88@gmail.com> on 2016-03-18.
 */
public interface SearchVenuesView {

    void showVenues(List<VenueModel> venues);

    void showPermissionsInfo();
}
