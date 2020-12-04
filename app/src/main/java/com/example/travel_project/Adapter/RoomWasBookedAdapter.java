package com.example.travel_project.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travel_project.DetailsTourWasBooked_Activity;
import com.example.travel_project.Model.DetailsHotelBookData;
import com.example.travel_project.Model.TourWasBookedData;
import com.example.travel_project.NetWork.RoomWasBookedDelete;
import com.example.travel_project.NetWork.TourWasBookedDelete;
import com.example.travel_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RoomWasBookedAdapter extends RecyclerView.Adapter<RoomWasBookedAdapter.RoomWasBookedAdapterViewHolder> {
    private Context context;
    private List<DetailsHotelBookData> detailsHotelBookDataList;

    public RoomWasBookedAdapter(Context context, List<DetailsHotelBookData> detailsHotelBookDataList) {
        this.context = context;
        this.detailsHotelBookDataList = detailsHotelBookDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RoomWasBookedAdapter.RoomWasBookedAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.roomwasbooked_row_item, viewGroup, false);
        return new RoomWasBookedAdapter.RoomWasBookedAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RoomWasBookedAdapter.RoomWasBookedAdapterViewHolder roomWasBookedAdapterViewHolder, final int i) {
        final DetailsHotelBookData detailsHotelBookData = detailsHotelBookDataList.get(i);
        roomWasBookedAdapterViewHolder.tvTenKhachSan.setText(detailsHotelBookData.getHotelName());
        roomWasBookedAdapterViewHolder.tvLoaiGiuong.setText("Loại giường: " + detailsHotelBookData.getBedType());
        roomWasBookedAdapterViewHolder.tvEmail.setText("Email người đặt: " + detailsHotelBookData.getUserName());
        roomWasBookedAdapterViewHolder.tvNgayNhanPhong.setText("Ngày nhận phòng: " + detailsHotelBookData.getCheckIn());
        roomWasBookedAdapterViewHolder.tvGiaTien.setText("Giá tiền: " + detailsHotelBookData.getPrice() + " VNĐ");
        roomWasBookedAdapterViewHolder.tvNgayTraPhong.setText("Ngày trả phòng: " + detailsHotelBookData.getCheckOut());
        Picasso.get()
                .load(detailsHotelBookData.getImageUrl())
                .fit()
                .centerCrop()
                .into(roomWasBookedAdapterViewHolder.imgHinhAnhThanhPho);
        roomWasBookedAdapterViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        roomWasBookedAdapterViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialogMess = new AlertDialog.Builder(context);
                dialogMess.setMessage("Bạn muốn xóa phòng này?");
                dialogMess.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String roomWasBookedDataHotelName = detailsHotelBookData.getHotelName();

                        Task<Void> voidTask = RoomWasBookedDelete
                                .deleteRoom(roomWasBookedDataHotelName);

                        voidTask.addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Đã xóa dữ liệu từ firebase", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                dialogMess.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialogMess.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return detailsHotelBookDataList.size();
    }

    public class RoomWasBookedAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTenKhachSan, tvNgayNhanPhong, tvGiaTien, tvNgayTraPhong, tvLoaiGiuong, tvEmail;
        public ImageView imgHinhAnhThanhPho;

        public RoomWasBookedAdapterViewHolder(View view) {
            super(view);
            tvTenKhachSan = (TextView) view.findViewById(R.id.place_hotelNameRoomWasBooked);
            tvNgayNhanPhong = (TextView) view.findViewById(R.id.tv_checkInRoomWasBooked);
            tvNgayTraPhong = (TextView) view.findViewById(R.id.tv_checkOutRoomWasBooked);
            tvEmail = (TextView) view.findViewById(R.id.tv_EmailUserRoomWasBooked);
            tvGiaTien = (TextView) view.findViewById(R.id.priceRoomWasBooked);
            tvLoaiGiuong = (TextView) view.findViewById(R.id.tv_BedTypeRoomWasBooked);
            imgHinhAnhThanhPho = (ImageView) view.findViewById(R.id.place_imageRoomWasBooked);
        }
    }
}
