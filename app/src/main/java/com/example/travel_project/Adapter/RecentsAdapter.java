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

import com.example.travel_project.Model.RecentsData;
import com.example.travel_project.R;
import com.example.travel_project.DetailsCity_Activity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecentsAdapter extends RecyclerView.Adapter<RecentsAdapter.RecentsViewHolder> {

    Context context;
    List<RecentsData> recentsDataList;

    public RecentsAdapter(Context context) {
        this.context = context;
        this.recentsDataList = recentsDataList;
    }
    public void setData(List<RecentsData> data) {
        this.recentsDataList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recents_row_item, parent, false);

        // here we create a recyclerview row item layout file
        return new RecentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecentsViewHolder holder, final int position) {
        final RecentsData recentsData = recentsDataList.get(position);
        holder.countryName.setText(recentsData.getCountryName());
        holder.placeName.setText(recentsData.getPlaceName());
        Picasso.get()
                .load(recentsData.getImageUrl())
                .into(holder.placeImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsCity_Activity.class);
                intent.putExtra("placeName", recentsDataList.get(position).getPlaceName());
                intent.putExtra("countryName", recentsDataList.get(position).getCountryName());
                intent.putExtra("price", recentsDataList.get(position).getPrice());
                intent.putExtra("imageUrl", recentsDataList.get(position).getImageUrl());
                intent.putExtra("dePartures", recentsDataList.get(position).getDePartures());
                intent.putExtra("desCription", recentsDataList.get(position).getDesCription());
                intent.putExtra("img1", recentsDataList.get(position).getImageUrl_1());
                intent.putExtra("img2", recentsDataList.get(position).getImageUrl_2());
                intent.putExtra("img3", recentsDataList.get(position).getImageUrl_3());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (recentsDataList == null)
            return 0;
        return recentsDataList.size();
    }

    public static class RecentsViewHolder extends RecyclerView.ViewHolder {

        ImageView placeImage;
        TextView placeName, countryName, price;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);

            placeImage = itemView.findViewById(R.id.place_image);
            placeName = itemView.findViewById(R.id.place_name);
            countryName = itemView.findViewById(R.id.country_name);

        }

    }

}

