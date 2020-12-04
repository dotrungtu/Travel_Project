package com.example.travel_project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.travel_project.Adapter.BookingHoTel_InDaNangAdapter;
import com.example.travel_project.Model.BookingDaNangData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListBookRoomHotelInDaNang_Activity extends AppCompatActivity {
    RecyclerView recyclerViewBookRoom;
    List<BookingDaNangData> bookingDataList;
    BookingHoTel_InDaNangAdapter bookingHoTel_inDaNangAdapter;
    DatabaseReference databaseReference;
    ChildEventListener childEventListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookroom_hotel_indanang);


        recyclerViewBookRoom = (RecyclerView) findViewById(R.id.rcv_BookRoomHotelDaNang);
        recyclerViewBookRoom.setHasFixedSize(true);
        recyclerViewBookRoom.setLayoutManager(new LinearLayoutManager(this));

        bookingDataList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("HotelDaNang");

        bookingHoTel_inDaNangAdapter = new BookingHoTel_InDaNangAdapter(ListBookRoomHotelInDaNang_Activity.this, bookingDataList);

        recyclerViewBookRoom.setAdapter(bookingHoTel_inDaNangAdapter);

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BookingDaNangData bookingDaNangData = dataSnapshot.getValue(BookingDaNangData.class);
                bookingDaNangData.setHotelName(dataSnapshot.child("hotelName").getValue().toString());
                bookingDataList.add(bookingDaNangData);
                bookingHoTel_inDaNangAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                BookingDaNangData bookingDaNangData = dataSnapshot.getValue(BookingDaNangData.class);
                bookingDaNangData.setHotelName(dataSnapshot.child("hotelName").getValue().toString());
                bookingDataList.remove(bookingDaNangData);
                bookingHoTel_inDaNangAdapter.notifyDataSetChanged();
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

