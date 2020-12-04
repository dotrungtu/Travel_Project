package com.example.travel_project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travel_project.Adapter.ListCityBookingAdapter;
import com.example.travel_project.Model.CityBookingData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListCityBooking_Fragment extends Fragment {
    RecyclerView cityBookingRecycler;
    ListCityBookingAdapter listCityBookingAdapter;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ImageView ivImageUserBooking;
    List<CityBookingData> cityBookingDataList = new ArrayList<>();
    TextView tvTenUserBooking, tvEmailUserBooking;
    EditText edtFilter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_citybooking, container, false);
        ivImageUserBooking = (ImageView) view.findViewById(R.id.iv_ImageUserBooking);

        tvTenUserBooking = (TextView) view.findViewById(R.id.textViewBooking1);
        tvEmailUserBooking = (TextView) view.findViewById(R.id.textViewBooking2);

        edtFilter = (EditText) view.findViewById(R.id.edtSearchBooking);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");
        Query query = databaseReference.orderByChild("email").equalTo(firebaseUser.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String image = "" + dataSnapshot1.child("image").getValue();
                    String email = "" + dataSnapshot1.child("email").getValue();
                    String name = "" + dataSnapshot1.child("name").getValue();

                    tvEmailUserBooking.setText(email);
                    tvTenUserBooking.setText(name);

                    try {
                        Picasso.get()
                                .load(image)
                                .transform(new CircleTransform())
                                .into(ivImageUserBooking);
                    } catch (Exception e) {
                        Picasso.get()
                                .load(image)
                                .transform(new CircleTransform())
                                .into(ivImageUserBooking);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        cityBookingDataList = new ArrayList<>();

        cityBookingRecycler = (RecyclerView) view.findViewById(R.id.citybookingroom_recycler);

        RecyclerView.LayoutManager layoutManagerRecent = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        cityBookingRecycler.setLayoutManager(layoutManagerRecent);
        listCityBookingAdapter = new ListCityBookingAdapter(getContext());
        cityBookingRecycler.setAdapter(listCityBookingAdapter);

        DatabaseReference databaseReferenceRecent = FirebaseDatabase.getInstance().getReference("CityBooking");
        databaseReferenceRecent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    CityBookingData cityBookingData = dataSnapshot1.getValue(CityBookingData.class);
                    cityBookingDataList.add(cityBookingData);
                }
                handleDataBooking();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        edtFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handleDataBooking();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;

    }
    private void handleDataBooking() {
        Log.e("Error", "THISSS");
        String searchText = edtFilter.getText().toString().toLowerCase();
        if (cityBookingDataList.isEmpty())
            return;
        List<CityBookingData> cityBookingData = cityBookingDataList.stream()
                .filter(p -> {
                    if (p.getCityName() == null || p.getCityName().isEmpty()) return false;
                    return p.getCityName().toLowerCase().contains(searchText);
                }).collect(Collectors.toList());
        listCityBookingAdapter.setData(cityBookingData);
    }
}



