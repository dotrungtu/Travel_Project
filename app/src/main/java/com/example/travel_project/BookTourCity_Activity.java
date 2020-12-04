package com.example.travel_project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travel_project.Model.TourData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class BookTourCity_Activity extends AppCompatActivity {
    ImageView imgTrangChu, imgTitle1, imgTitle2, imgTitle3;
    TextView tvTenThanhPho, tvNgayKhoiHanh, tvGiaTien, tvThongTinChuyenDi, tvEmail;
    Button btnDatTour;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booktour);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");

        imgTrangChu = (ImageView) findViewById(R.id.imageView3_BookTour);
        imgTitle1 = (ImageView) findViewById(R.id.imageView8_BookTour);
        imgTitle2 = (ImageView) findViewById(R.id.imageView9_BookTour);
        imgTitle3 = (ImageView) findViewById(R.id.imageView10_BookTour);
        tvTenThanhPho = (TextView) findViewById(R.id.textView6_BookTour);
        tvNgayKhoiHanh = (TextView) findViewById(R.id.textView10_BookTour);
        tvGiaTien = (TextView) findViewById(R.id.textView11_BookTour);
        tvThongTinChuyenDi = (TextView) findViewById(R.id.textView14_BookTour);
        tvEmail = (TextView) findViewById(R.id.textView7_BookTour);
        btnDatTour = (Button) findViewById(R.id.button_BookTour);
        String image = getIntent().getStringExtra("imageUrl");
        String cityName = getIntent().getStringExtra("placeName");
        String countryName = getIntent().getStringExtra("countryName");
        String dePartures = getIntent().getStringExtra("dePartures");
        String price = getIntent().getStringExtra("price");
        String img1 = getIntent().getStringExtra("img1");
        String img2 = getIntent().getStringExtra("img2");
        String img3 = getIntent().getStringExtra("img3");
        String tripInformation = getIntent().getStringExtra("tripInformation");
        String desCription = getIntent().getStringExtra("desCription");

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
        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //check until required data get

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //get data
                    String email = "" + ds.child("email").getValue();
                    //set data
                    tvEmail.setText(email);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnDatTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = tvEmail.getText().toString().trim();
                bookTour(cityName, countryName, price, image, dePartures, desCription, img1, img2, img3, tripInformation, email);
            }
        });

    }

    public boolean bookTour(String placeName, String countryName, String price, String imageUrl,
                                String dePartures, String desCription, String imageUrl_1, String imageUrl_2, String imageUrl_3, String tripInformation, String email) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Tour").child(placeName);
        TourData tourData = new TourData(
                placeName, countryName, price, imageUrl, dePartures, desCription, imageUrl_1, imageUrl_2, imageUrl_3, tripInformation, email);
        databaseReference.setValue(tourData);
        Toast.makeText(BookTourCity_Activity.this, "Đặt tour thành công", Toast.LENGTH_SHORT).show();
        return true;
    }
}
