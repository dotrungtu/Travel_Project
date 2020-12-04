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
import com.example.travel_project.Model.BookingDaNangData;
import com.example.travel_project.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookingHoTel_InDaNangAdapter extends RecyclerView.Adapter<BookingHoTel_InDaNangAdapter.BookingViewHolder> {

    Context context;
    List<BookingDaNangData> bookingDataList;

    public BookingHoTel_InDaNangAdapter(Context context, List<BookingDaNangData> bookingDataList) {
        this.context = context;
        this.bookingDataList = bookingDataList;
    }

    @NonNull
    @Override
    public BookingHoTel_InDaNangAdapter.BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.bookinghoteldanang_row_item, parent, false);

        // here we create a recyclerview row item layout file
        return new BookingHoTel_InDaNangAdapter.BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookingHoTel_InDaNangAdapter.BookingViewHolder holder, final int position) {
        final BookingDaNangData bookingDaNangData = bookingDataList.get(position);

        holder.tvTenKhachSan.setText(bookingDaNangData.getHotelName());
        holder.tvKhuVuc.setText("Khu vực: " + bookingDaNangData.getPlaceName());
        holder.tvGiaTien.setText(bookingDaNangData.getPrice() + " VNĐ");
        Picasso.get()
                .load(bookingDaNangData.getImageUrl())
                .into(holder.imgPhong);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsHotelBook_Activity.class);
                intent.putExtra("hotelName", bookingDataList.get(position).getHotelName());
                intent.putExtra("placeName", bookingDataList.get(position).getPlaceName());
                intent.putExtra("imageUrl", bookingDataList.get(position).getImageUrl());
                intent.putExtra("price", bookingDataList.get(position).getPrice());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingDataList.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPhong;
        TextView tvTenKhachSan, tvGiaTien, tvKhuVuc;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPhong = (ImageView) itemView.findViewById(R.id.img_BookingDaNang);
            tvTenKhachSan = (TextView) itemView.findViewById(R.id.tv_HotelNameDaNang);
            tvGiaTien = (TextView) itemView.findViewById(R.id.tv_PriceBookingDaNang);
            tvKhuVuc= (TextView) itemView.findViewById(R.id.tv_AreaDaNang);

        }
    }
}