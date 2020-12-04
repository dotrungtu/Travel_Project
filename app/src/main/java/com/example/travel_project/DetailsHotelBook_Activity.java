package com.example.travel_project;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travel_project.Model.DetailsHotelBookData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetailsHotelBook_Activity extends AppCompatActivity {
    ImageView imgHinhAnhKhachSan;
    TextView tvTenKhachSan, tvDiaChiKhachSan;
    EditText edtCheckIn, edtCheckOut, edtTenKhachHang, edtSoDienThoai;
    RadioButton radioGiuongDon, radioGiuongDoi;
    Button btnCheckIn, btnCheckOut, btnDatPhong;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailshotelwasbooked);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");

        tvTenKhachSan = (TextView) findViewById(R.id.tv_TenKhachSanDatPhong);
        tvDiaChiKhachSan = (TextView) findViewById(R.id.tv_DiaChiKhachSanDatPhong);
        edtCheckIn = (EditText) findViewById(R.id.edt_CheckIn);
        edtCheckOut = (EditText) findViewById(R.id.edt_CheckOut);
        edtTenKhachHang = (EditText) findViewById(R.id.edt_UserName);
        edtSoDienThoai = (EditText) findViewById(R.id.edt_PhoneNumber);
        btnCheckIn = (Button) findViewById(R.id.btn_DateCheckIn);
        btnCheckOut = (Button) findViewById(R.id.btn_DateCheckOut);
        btnDatPhong = (Button) findViewById(R.id.btn_DatPhong);
        radioGiuongDoi = (RadioButton) findViewById(R.id.radio_GiuongDoi);
        radioGiuongDon = (RadioButton) findViewById(R.id.radio_GiuongDon);
        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //check until required data get

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //get data
                    String email = "" + ds.child("email").getValue();
                    String phoneNumber = "" + ds.child("phone").getValue();

                    //set data
                    edtTenKhachHang.setText(email);
                    edtSoDienThoai.setText(phoneNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        imgHinhAnhKhachSan = (ImageView) findViewById(R.id.img_HinhAnhKhachSanDatPhong);
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String hotelName = getIntent().getStringExtra("hotelName");
        String placeName = getIntent().getStringExtra("placeName");
        String price = getIntent().getStringExtra("price");

        tvTenKhachSan.setText(hotelName);
        tvDiaChiKhachSan.setText(placeName);
        Picasso.get()
                .load(imageUrl)
                .into(imgHinhAnhKhachSan);
        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgayDatPhong();
            }
        });
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgayTraPhong();
            }
        });
        btnDatPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioGiuongDoi.isChecked())
                {
                    String userName = edtTenKhachHang.getText().toString();
                    String phoneNumber = edtSoDienThoai.getText().toString();
                    String checkIn = edtCheckIn.getText().toString().trim();
                    String checkOut = edtCheckOut.getText().toString().trim();
                    String bedType = radioGiuongDoi.getText().toString().trim();
                    datPhong(imageUrl, hotelName, placeName, userName, phoneNumber, checkIn, checkOut, price, bedType);
                }
                if(radioGiuongDon.isChecked())
                {
                    String userName = edtTenKhachHang.getText().toString();
                    String phoneNumber = edtSoDienThoai.getText().toString();
                    String checkIn = edtCheckIn.getText().toString().trim();
                    String checkOut = edtCheckOut.getText().toString().trim();
                    String bedType = radioGiuongDon.getText().toString().trim();
                    datPhong(imageUrl, hotelName, placeName, userName, phoneNumber, checkIn, checkOut, price, bedType);
                }

            }
        });
    }

    private void chonNgayDatPhong() {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String date2 = dayOfMonth + "/" + month
                        + "/" + year;
                edtCheckIn.setText(date2);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void chonNgayTraPhong() {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String date1 = dayOfMonth + "/" + month
                        + "/" + year;
                edtCheckOut.setText(date1);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public boolean datPhong(String imageUrl, String hotelName, String placeName, String userName, String phoneNumber,
                            String checkIn, String checkOut, String price, String bedType) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("HotelWasBooked").child(hotelName);
        DetailsHotelBookData detailsHotelBookData = new DetailsHotelBookData(
                imageUrl, hotelName, placeName, userName, phoneNumber, checkIn, checkOut, price, bedType);
        databaseReference.setValue(detailsHotelBookData);
        Toast.makeText(DetailsHotelBook_Activity.this, "Đặt phòng thành công", Toast.LENGTH_SHORT).show();
        return true;
    }
}
