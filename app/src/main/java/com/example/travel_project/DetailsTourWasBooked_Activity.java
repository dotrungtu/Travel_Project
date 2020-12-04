package com.example.travel_project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsTourWasBooked_Activity extends AppCompatActivity {
    ImageView imgTrangChu, imgTitle1, imgTitle2, imgTitle3;
    TextView tvTenThanhPho, tvNgayKhoiHanh, tvGiaTien, tvThongTinChuyenDi;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailstourwasbooked);
        imgTrangChu = (ImageView) findViewById(R.id.imageView3_DetailsBookTour);
        imgTitle1 = (ImageView) findViewById(R.id.imageView8_DetailsBookTour);
        imgTitle2 = (ImageView) findViewById(R.id.imageView9_DetailsBookTour);
        imgTitle3 = (ImageView) findViewById(R.id.imageView10_DetailsBookTour);
        tvNgayKhoiHanh = (TextView) findViewById(R.id.textView10_DetailsBookTour);
        tvGiaTien = (TextView) findViewById(R.id.textView11_DetailsBookTour);
        tvThongTinChuyenDi = (TextView) findViewById(R.id.textView14_DetailsBookTour);
        tvTenThanhPho = (TextView) findViewById(R.id.textView6_NameCityBookTour);

        String image = getIntent().getStringExtra("imageUrlTourWasBooked");
        String cityName = getIntent().getStringExtra("placeNameTourWasBooked");
        String countryName = getIntent().getStringExtra("countryNameTourWasBooked");
        String dePartures = getIntent().getStringExtra("deParturesTourWasBooked");
        String price = getIntent().getStringExtra("priceTourWasBooked");
        String img1 = getIntent().getStringExtra("img1TourWasBooked");
        String img2 = getIntent().getStringExtra("img2TourWasBooked");
        String img3 = getIntent().getStringExtra("img3TourWasBooked");
        String tripInformation = getIntent().getStringExtra("tripInformationTourWasBooked");

        tvTenThanhPho.setText(cityName + " , " + countryName);
        tvNgayKhoiHanh.setText(dePartures);
        tvGiaTien.setText(price);
        tvThongTinChuyenDi.setText(tripInformation);

        Picasso.get()
                .load(image)
                .into(imgTrangChu);

        Picasso.get()
                .load(img1)
                .into(imgTitle1);

        Picasso.get()
                .load(img2)
                .into(imgTitle2);

        Picasso.get()
                .load(img3)
                .into(imgTitle3);

    }
}
