package com.example.travel_project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.travel_project.Adapter.BookingHoTel_InDaLatAdapter;
import com.example.travel_project.Model.BookingDaLatData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListBookRoomHotelInDaLat_Activity extends AppCompatActivity {
    RecyclerView recyclerViewBookRoom;
    List<BookingDaLatData> bookingDaLatDataList;
    BookingHoTel_InDaLatAdapter bookingHoTelInDaLatAdapter;
    DatabaseReference databaseReference;
    ChildEventListener childEventListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookroom_hotel_indalat);


        recyclerViewBookRoom = (RecyclerView) findViewById(R.id.rcv_BookRoomHotelDaLat);
        recyclerViewBookRoom.setHasFixedSize(true);
        recyclerViewBookRoom.setLayoutManager(new LinearLayoutManager(this));

        bookingDaLatDataList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("HotelInDaLat");

        bookingHoTelInDaLatAdapter = new BookingHoTel_InDaLatAdapter(ListBookRoomHotelInDaLat_Activity.this, bookingDaLatDataList);

        recyclerViewBookRoom.setAdapter(bookingHoTelInDaLatAdapter);

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BookingDaLatData bookingDaLatData = dataSnapshot.getValue(BookingDaLatData.class);
                bookingDaLatData.setHotelName(dataSnapshot.child("hotelName").getValue().toString());
                bookingDaLatDataList.add(bookingDaLatData);
                bookingHoTelInDaLatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                BookingDaLatData bookingDaLatData = dataSnapshot.getValue(BookingDaLatData.class);
                bookingDaLatData.setHotelName(dataSnapshot.child("hotelName").getValue().toString());
                bookingDaLatDataList.remove(bookingDaLatData);
                bookingHoTelInDaLatAdapter.notifyDataSetChanged();
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

