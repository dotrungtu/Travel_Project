package com.example.travel_project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.travel_project.Adapter.RoomWasBookedAdapter;
import com.example.travel_project.Adapter.TourWasBookedAdapter;
import com.example.travel_project.Model.DetailsHotelBookData;
import com.example.travel_project.Model.TourWasBookedData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListRoomWasBooked_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    RoomWasBookedAdapter roomWasBookedAdapter;
    List<DetailsHotelBookData> detailsHotelBookDataList;
    ChildEventListener childEventListener;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listroomwasbooked);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");

        recyclerView = (RecyclerView) findViewById(R.id.rcv_LichSuDatPhong);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        detailsHotelBookDataList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("HotelWasBooked");

        roomWasBookedAdapter = new RoomWasBookedAdapter(ListRoomWasBooked_Activity.this, detailsHotelBookDataList);

        recyclerView.setAdapter(roomWasBookedAdapter);

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                DetailsHotelBookData detailsHotelBookData = dataSnapshot.getValue(DetailsHotelBookData.class);
                detailsHotelBookData.setPlaceName(dataSnapshot.child("hotelName").getValue().toString());
                if(detailsHotelBookData.getUserName().equals(user.getEmail())){
                    detailsHotelBookDataList.add(detailsHotelBookData);
                    roomWasBookedAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                DetailsHotelBookData detailsHotelBookData = dataSnapshot.getValue(DetailsHotelBookData.class);
                detailsHotelBookData.setPlaceName(dataSnapshot.child("hotelName").getValue().toString());
                detailsHotelBookDataList.remove(detailsHotelBookData);
                roomWasBookedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addChildEventListener(childEventListener);
    }
}
