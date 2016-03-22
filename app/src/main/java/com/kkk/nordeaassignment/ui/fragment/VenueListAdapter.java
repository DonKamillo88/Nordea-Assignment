package com.kkk.nordeaassignment.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kkk.nordeaassignment.R;
import com.kkk.nordeaassignment.ui.model.VenueModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kamil Popławski <Kamil.Poplawski88@gmail.com> on 2016-03-17.
 */
public class VenueListAdapter extends RecyclerView.Adapter<VenueListAdapter.VenueViewHolder> {

    private List<VenueModel> mCountryModel;

    public VenueListAdapter(List<VenueModel> mCountryModel) {
        this.mCountryModel = mCountryModel;
    }

    @Override
    public void onBindViewHolder(VenueViewHolder venueViewHolder, int i) {
        final VenueModel model = mCountryModel.get(i);
        venueViewHolder.bind(model);
    }

    @Override
    public VenueViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.venue_item, viewGroup, false);
        return new VenueViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mCountryModel.size();
    }

    public void setFilter(List<VenueModel> countryModels) {
        mCountryModel = new ArrayList<>();
        mCountryModel.addAll(countryModels);
        notifyDataSetChanged();
    }

    static class VenueViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView address;
        public TextView distance;

        public VenueViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            address = (TextView) itemView.findViewById(R.id.address);
            distance = (TextView) itemView.findViewById(R.id.distance);
        }

        public void bind(VenueModel venueModel) {
            name.setText(venueModel.getName());
            address.setText(venueModel.getAddress());
            distance.setText(String.valueOf(venueModel.getDistance()));
        }
    }

}