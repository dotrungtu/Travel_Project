package com.example.travel_project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AdminActivity extends AppCompatActivity {
    TextView tvTenThanhPho;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        tvTenThanhPho = (TextView) findViewById(R.id.tvAdmin);

        String cityName = getIntent().getStringExtra("cityName");

        tvTenThanhPho.setText("Tên thành phố: " + cityName);
    }
}
