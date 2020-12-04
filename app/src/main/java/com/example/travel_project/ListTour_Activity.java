package com.example.travel_project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.travel_project.Adapter.TourWasBookedAdapter;
import com.example.travel_project.Model.TourData;
import com.example.travel_project.Model.TourWasBookedData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListTour_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    TourWasBookedAdapter tourWasBookedAdapter;
    DatabaseReference databaseReference;
    List<TourWasBookedData> tourWasBookedDataList;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    ChildEventListener childEventListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listtour);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");

        recyclerView = (RecyclerView) findViewById(R.id.rcv_HinhAnh);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tourWasBookedDataList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Tour");

        tourWasBookedAdapter = new TourWasBookedAdapter(ListTour_Activity.this, tourWasBookedDataList);

        recyclerView.setAdapter(tourWasBookedAdapter);

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TourWasBookedData tourWasBookedData = dataSnapshot.getValue(TourWasBookedData.class);
                tourWasBookedData.setPlaceName(dataSnapshot.child("placeName").getValue().toString());

                if(tourWasBookedData.getEmail().equals(user.getEmail())) {
                    tourWasBookedDataList.add(tourWasBookedData);
                    tourWasBookedAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                TourWasBookedData tourWasBookedData = dataSnapshot.getValue(TourWasBookedData.class);
                tourWasBookedData.setPlaceName(dataSnapshot.child("placeName").getValue().toString());
                tourWasBookedDataList.remove(tourWasBookedData);
                tourWasBookedAdapter.notifyDataSetChanged();
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
