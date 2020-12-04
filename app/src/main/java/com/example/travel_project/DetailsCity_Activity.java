package com.example.travel_project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class DetailsCity_Activity extends AppCompatActivity
{
    ImageView imgTitle, imgBack, img1, img2, img3;
    TextView tvTenThanhPho, tvNgayKhoiHanh, tvGiaTien, tvMoTa;
    Button btnDatPhong;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailscity);

        imgTitle = (ImageView) findViewById(R.id.imageView3);
        imgBack = (ImageView) findViewById(R.id.imageView4);
        img1 = (ImageView) findViewById(R.id.imageView8);
        img2 = (ImageView) findViewById(R.id.imageView9);
        img3 = (ImageView) findViewById(R.id.imageView10);
        tvTenThanhPho = (TextView) findViewById(R.id.textView6);
        tvMoTa = (TextView) findViewById(R.id.textView14);

        imgTitle.setMaxHeight(1792);
        imgTitle.setMaxWidth(1792);
        String imageTitle = getIntent().getStringExtra("imageUrl");
        String cityName = getIntent().getStringExtra("placeName");
        String country = getIntent().getStringExtra("countryName");
        String desCription = getIntent().getStringExtra("desCription");
        String image1 = getIntent().getStringExtra("img1");
        String image2 = getIntent().getStringExtra("img2");
        String image3 = getIntent().getStringExtra("img3");

        tvTenThanhPho.setText(cityName + " , " + country);
        tvMoTa.setText(desCription);
        Picasso.get()
                .load(imageTitle)
                .into(imgTitle);
        Picasso.get()
                .load(image1)
                .into(img1);
        Picasso.get()
                .load(image2)
                .into(img2);
        Picasso.get()
                .load(image3)
                .into(img3);

    }


//    public boolean truyenDuLieu(String giaTien, String moTa, String tenPhong, String hinhAnh, String email, String dichVuGiatUiQuanAo,
//                                String dichVuChuyenPhat, String dichVuVanPhong, String dichVuXeDuaDonRaSanBay, String dichVuTrongTreEm, String ngayDatPhong, String tinhTrangDatPhong) {
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("CacPhongDaDat").child(tenPhong);
//        CacPhongDaDat cacPhongDaDat = new CacPhongDaDat(
//                giaTien, moTa, tenPhong, hinhAnh, email, dichVuGiatUiQuanAo, dichVuChuyenPhat, dichVuVanPhong, dichVuXeDuaDonRaSanBay, dichVuTrongTreEm, ngayDatPhong, tinhTrangDatPhong);
//        databaseReference.setValue(cacPhongDaDat);
//        Toast.makeText(DatPhongKhachSanActivity.this, "Đặt phòng thành công", Toast.LENGTH_SHORT).show();
//        return true;
//    }
}
