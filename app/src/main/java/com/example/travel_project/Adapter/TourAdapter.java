package com.example.travel_project.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travel_project.BookTourCity_Activity;
import com.example.travel_project.DetailsCity_Activity;
import com.example.travel_project.Model.TourData;
import com.example.travel_project.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.TopPlacesViewHolder> {

    Context context;
    List<TourData> tourAdapterList;

    public TourAdapter(Context context, List<TourData> data) {
        this.context = context;
        this.tourAdapterList = data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TopPlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.booktour_row_item, parent, false);

        // here we create a recyclerview row item layout file
        return new TopPlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopPlacesViewHolder holder, final int position) {
        final TourData tourData = tourAdapterList.get(position);
        holder.countryName.setText(tourData.getCountryName());
        holder.placeName.setText(tourData.getPlaceName());
        holder.price.setText(tourData.getPrice());

        try {
            Picasso.get()
                    .load(tourData.getImageUrl())
                    .into(holder.placeImage);

        } catch (Exception e) {
            Picasso.get()
                    .load(tourData.getImageUrl())
                    .into(holder.placeImage);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookTourCity_Activity.class);
                intent.putExtra("placeName", tourAdapterList.get(position).getPlaceName());
                intent.putExtra("countryName", tourAdapterList.get(position).getCountryName());
                intent.putExtra("price", tourAdapterList.get(position).getPrice());
                intent.putExtra("imageUrl", tourAdapterList.get(position).getImageUrl());
                intent.putExtra("dePartures", tourAdapterList.get(position).getDePartures());
                intent.putExtra("desCription", tourAdapterList.get(position).getDesCription());
                intent.putExtra("img1", tourAdapterList.get(position).getImageUrl_1());
                intent.putExtra("img2", tourAdapterList.get(position).getImageUrl_2());
                intent.putExtra("img3", tourAdapterList.get(position).getImageUrl_3());
                intent.putExtra("tripInformation", tourAdapterList.get(position).getTripInformation());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (tourAdapterList == null)
            return 0;
        return tourAdapterList.size();
    }

    public static final class TopPlacesViewHolder extends RecyclerView.ViewHolder {

        ImageView placeImage;
        TextView placeName, countryName, price;

        public TopPlacesViewHolder(@NonNull View itemView) {
            super(itemView);

            placeImage = itemView.findViewById(R.id.place_image);
            placeName = itemView.findViewById(R.id.place_name);
            countryName = itemView.findViewById(R.id.country_name);
            price = itemView.findViewById(R.id.price);

        }
    }
}
