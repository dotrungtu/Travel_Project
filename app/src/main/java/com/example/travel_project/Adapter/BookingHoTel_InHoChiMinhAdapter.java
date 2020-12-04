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
import com.example.travel_project.Model.BookingHoChiMinhData;
import com.example.travel_project.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookingHoTel_InHoChiMinhAdapter extends RecyclerView.Adapter<BookingHoTel_InHoChiMinhAdapter.BookingViewHolder> {

    Context context;
    List<BookingHoChiMinhData> bookingDataList;

    public BookingHoTel_InHoChiMinhAdapter(Context context, List<BookingHoChiMinhData> bookingDataList) {
        this.context = context;
        this.bookingDataList = bookingDataList;
    }

    @NonNull
    @Override
    public BookingHoTel_InHoChiMinhAdapter.BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.bookinghotelhochiminh_row_item, parent, false);

        // here we create a recyclerview row item layout file
        return new BookingHoTel_InHoChiMinhAdapter.BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookingHoTel_InHoChiMinhAdapter.BookingViewHolder holder, final int position) {
        final BookingHoChiMinhData bookingHoChiMinhData = bookingDataList.get(position);

        holder.tvTenKhachSan.setText(bookingHoChiMinhData.getHotelName());
        holder.tvKhuVuc.setText("Khu vực: " + bookingHoChiMinhData.getPlaceName());
        holder.tvGiaTien.setText(bookingHoChiMinhData.getPrice() + " VNĐ");
        Picasso.get()
                .load(bookingHoChiMinhData.getImageUrl())
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

            imgPhong = (ImageView) itemView.findViewById(R.id.img_BookingHoChiMinh);
            tvTenKhachSan = (TextView) itemView.findViewById(R.id.tv_HotelNameHoChiMinh);
            tvGiaTien = (TextView) itemView.findViewById(R.id.tv_PriceBookingHoChiMinh);
            tvKhuVuc= (TextView) itemView.findViewById(R.id.tv_AreaHoChiMinh);

        }
    }
}