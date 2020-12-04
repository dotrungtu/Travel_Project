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

import com.example.travel_project.DetailsHotelBook_Activity;
import com.example.travel_project.Model.BookingDaLatData;
import com.example.travel_project.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookingHoTel_InDaLatAdapter extends RecyclerView.Adapter<BookingHoTel_InDaLatAdapter.BookingViewHolder> {

    Context context;
    List<BookingDaLatData> bookingDaLatDataList;

    public BookingHoTel_InDaLatAdapter(Context context, List<BookingDaLatData> bookingDaLatDataList) {
        this.context = context;
        this.bookingDaLatDataList = bookingDaLatDataList;
    }

    @NonNull
    @Override
    public BookingHoTel_InDaLatAdapter.BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.bookinghoteldalat_row_item, parent, false);

        // here we create a recyclerview row item layout file
        return new BookingHoTel_InDaLatAdapter.BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookingHoTel_InDaLatAdapter.BookingViewHolder holder, final int position) {
        final BookingDaLatData bookingDaLatData = bookingDaLatDataList.get(position);
        holder.tvTenKhachSan.setText(bookingDaLatData.getHotelName());
        holder.tvKhuVuc.setText("Khu vực: " + bookingDaLatData.getPlaceName());
        holder.tvGiaTien.setText(bookingDaLatData.getPrice() + " VNĐ");
        Picasso.get()
                .load(bookingDaLatData.getImageUrl())
                .into(holder.imgPhong);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsHotelBook_Activity.class);
                intent.putExtra("hotelName", bookingDaLatDataList.get(position).getHotelName());
                intent.putExtra("placeName", bookingDaLatDataList.get(position).getPlaceName());
                intent.putExtra("imageUrl", bookingDaLatDataList.get(position).getImageUrl());
                intent.putExtra("price", bookingDaLatDataList.get(position).getPrice());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingDaLatDataList.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPhong;
        TextView tvTenKhachSan, tvGiaTien, tvKhuVuc;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPhong = (ImageView) itemView.findViewById(R.id.img_BookingDaLat);
            tvTenKhachSan = (TextView) itemView.findViewById(R.id.tv_HotelNameDaLat);
            tvGiaTien = (TextView) itemView.findViewById(R.id.tv_PriceBookingDaLat);
            tvKhuVuc= (TextView) itemView.findViewById(R.id.tv_AreaDaLat);

        }
    }
}