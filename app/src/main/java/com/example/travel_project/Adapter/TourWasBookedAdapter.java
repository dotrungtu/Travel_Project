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
import com.example.travel_project.Model.TourWasBookedData;
import com.example.travel_project.NetWork.TourWasBookedDelete;
import com.example.travel_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TourWasBookedAdapter extends RecyclerView.Adapter<TourWasBookedAdapter.TourWasBookedAdapterViewHolder> {
    private Context context;
    private List<TourWasBookedData> tourWasBookedDataList;

    public TourWasBookedAdapter(Context context, List<TourWasBookedData> tourWasBookedDataList) {
        this.context = context;
        this.tourWasBookedDataList = tourWasBookedDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TourWasBookedAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.tourwasbooked_row_item, viewGroup, false);
        return new TourWasBookedAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final TourWasBookedAdapterViewHolder tourWasBookedAdapterViewHolder, final int i) {
        final TourWasBookedData tourWasBookedData = tourWasBookedDataList.get(i);
        tourWasBookedAdapterViewHolder.tvTenDatNuoc.setText(tourWasBookedData.getCountryName());
        tourWasBookedAdapterViewHolder.tvTenThanhPho.setText(tourWasBookedData.getPlaceName());
        tourWasBookedAdapterViewHolder.tvEmail.setText("Email người đặt: " + tourWasBookedData.getEmail());
        tourWasBookedAdapterViewHolder.tvGiaTien.setText("Giá tiền: " + tourWasBookedData.getPrice() + " VNĐ");
        tourWasBookedAdapterViewHolder.tvNgayKhoiHanh.setText("Ngày khởi hành: " + tourWasBookedData.getDePartures());
        Picasso.get()
                .load(tourWasBookedData.getImageUrl())
                .fit()
                .centerCrop()
                .into(tourWasBookedAdapterViewHolder.imgHinhAnhThanhPho);
        tourWasBookedAdapterViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsTourWasBooked_Activity.class);
                intent.putExtra("placeNameTourWasBooked", tourWasBookedDataList.get(i).getPlaceName());
                intent.putExtra("countryNameTourWasBooked", tourWasBookedDataList.get(i).getCountryName());
                intent.putExtra("priceTourWasBooked", tourWasBookedDataList.get(i).getPrice());
                intent.putExtra("imageUrlTourWasBooked", tourWasBookedDataList.get(i).getImageUrl());
                intent.putExtra("deParturesTourWasBooked", tourWasBookedDataList.get(i).getDePartures());
                intent.putExtra("desCriptionTourWasBooked", tourWasBookedDataList.get(i).getDesCription());
                intent.putExtra("img1TourWasBooked", tourWasBookedDataList.get(i).getImageUrl_1());
                intent.putExtra("img2TourWasBooked", tourWasBookedDataList.get(i).getImageUrl_2());
                intent.putExtra("img3TourWasBooked", tourWasBookedDataList.get(i).getImageUrl_3());
                intent.putExtra("tripInformationTourWasBooked", tourWasBookedDataList.get(i).getTripInformation());
                context.startActivity(intent);
            }
        });
        tourWasBookedAdapterViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialogMess = new AlertDialog.Builder(context);
                dialogMess.setMessage("Bạn muốn xóa tour này?");
                dialogMess.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tourWasBookedDataPlaceName = tourWasBookedData.getPlaceName();

                        Task<Void> voidTask = TourWasBookedDelete
                                .deleteBookTour(tourWasBookedDataPlaceName);

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
        return tourWasBookedDataList.size();
    }

    public class TourWasBookedAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTenThanhPho, tvTenDatNuoc, tvGiaTien, tvNgayKhoiHanh, tvEmail;
        public ImageView imgHinhAnhThanhPho;

        public TourWasBookedAdapterViewHolder(View view) {
            super(view);
            tvTenDatNuoc = (TextView) view.findViewById(R.id.country_nameWasBooked);
            tvEmail = (TextView) view.findViewById(R.id.tv_EmailUserWasBooked);
            tvTenThanhPho = (TextView) view.findViewById(R.id.place_nameWasBooked);
            tvGiaTien = (TextView) view.findViewById(R.id.priceWasBooked);
            tvNgayKhoiHanh = (TextView) view.findViewById(R.id.tv_checkInWasBooked);
            imgHinhAnhThanhPho = (ImageView) view.findViewById(R.id.place_imageWasBooked);
        }
    }
}
