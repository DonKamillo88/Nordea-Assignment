package com.kkk.nordeaassignment.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kkk.nordeaassignment.R;
import com.kkk.nordeaassignment.ui.model.VenueModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kamil Popławski <Kamil.Poplawski88@gmail.com> on 2016-03-17.
 */
public class SearchVenuesFragment extends Fragment implements SearchView.OnQueryTextListener, SearchVenuesView {

    private RecyclerView mRecyclerView;
    private List<VenueModel> mVenueModel;
    private VenueListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
        mVenueModel = new ArrayList<>();
        mAdapter = new VenueListAdapter(mVenueModel);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        mAdapter.setFilter(mVenueModel);
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }
                });
    }

    @Override
    public void showVenues(final List<VenueModel> venues) {
        Handler mainHandler = new Handler(getContext().getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                updateData(venues);
            }
        };
        mainHandler.post(myRunnable);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<VenueModel> filteredModelList = filter(mVenueModel, newText);
        mAdapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<VenueModel> filter(List<VenueModel> models, String query) {
        query = query.toLowerCase();

        final List<VenueModel> filteredModelList = new ArrayList<>();
        for (VenueModel model : models) {
            String name = "";
            String address = "";

            if (model.getName() != null)
                name = model.getName().toLowerCase();
            if (model.getAddress() != null)
                address = model.getAddress().toLowerCase();

            if (name.contains(query) || address.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    public void updateData(List<VenueModel> venues) {
        mVenueModel.clear();
        mVenueModel.addAll(venues);
        mAdapter.notifyDataSetChanged();
    }
}
