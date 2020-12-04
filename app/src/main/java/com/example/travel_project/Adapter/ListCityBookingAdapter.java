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

import com.example.travel_project.ListBookRoomHotelInDaLat_Activity;
import com.example.travel_project.ListBookRoomHotelInDaNang_Activity;
import com.example.travel_project.ListBookRoomHotelInHaNoi_Activity;
import com.example.travel_project.ListBookRoomHotelInHoChiMinh_Activity;
import com.example.travel_project.ListBookRoomHotelInSaPa_Activity;
import com.example.travel_project.Model.CityBookingData;
import com.example.travel_project.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListCityBookingAdapter extends RecyclerView.Adapter<ListCityBookingAdapter.CityBookingViewHolder> {

    Context context;
    List<CityBookingData> cityBookingDataList;

    public ListCityBookingAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CityBookingData> data) {
        this.cityBookingDataList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListCityBookingAdapter.CityBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.citybooking_row_item, parent, false);

        // here we create a recyclerview row item layout file
        return new ListCityBookingAdapter.CityBookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListCityBookingAdapter.CityBookingViewHolder holder, final int position) {
        final CityBookingData cityBookingData = cityBookingDataList.get(position);
        holder.tvCityName.setText(cityBookingData.getCityName());
        holder.tvCountryName.setText(cityBookingData.getCountryName());

        Picasso.get()
                .load(cityBookingData.getImageUrl())
                .into(holder.imgThanhPho);

        if (holder.tvCityName.getText().toString().equals("Thành phố Đà Lạt")) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ListBookRoomHotelInDaLat_Activity.class);
                    context.startActivity(intent);
                }
            });
        }
        if (holder.tvCityName.getText().toString().equals("Thành phố Hồ Chí Minh")) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ListBookRoomHotelInHoChiMinh_Activity.class);
                    context.startActivity(intent);
                }
            });
        }
        if (holder.tvCityName.getText().toString().equals("Thành phố Hà Nội")) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ListBookRoomHotelInHaNoi_Activity.class);
                    context.startActivity(intent);
                }
            });
        }
        if (holder.tvCityName.getText().toString().equals("Thành phố Đà Nẵng")) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ListBookRoomHotelInDaNang_Activity.class);
                    context.startActivity(intent);
                }
            });
        }
        if (holder.tvCityName.getText().toString().equals("Thành phố Sa Pa")) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ListBookRoomHotelInSaPa_Activity.class);
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (cityBookingDataList == null)
            return 0;
        return cityBookingDataList.size();
    }

    public static class CityBookingViewHolder extends RecyclerView.ViewHolder {

        ImageView imgThanhPho;
        TextView tvCityName, tvCountryName;

        public CityBookingViewHolder(@NonNull View itemView) {
            super(itemView);

            imgThanhPho = (ImageView) itemView.findViewById(R.id.img_CityBooking);
            tvCountryName = (TextView) itemView.findViewById(R.id.tv_CountryName);
            tvCityName = (TextView) itemView.findViewById(R.id.tv_CityName);


        }
    }
}
