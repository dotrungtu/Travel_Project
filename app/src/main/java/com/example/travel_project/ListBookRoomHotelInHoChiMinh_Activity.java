package com.example.travel_project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.travel_project.Adapter.BookingHoTel_InHoChiMinhAdapter;
import com.example.travel_project.Model.BookingHoChiMinhData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListBookRoomHotelInHoChiMinh_Activity extends AppCompatActivity {
    RecyclerView recyclerViewBookRoom;
    List<BookingHoChiMinhData> bookingDataList;
    BookingHoTel_InHoChiMinhAdapter bookingHoTel_inHoChiMinhAdapter;
    DatabaseReference databaseReference;
    ChildEventListener childEventListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookroom_hotel_inhochiminh);


        recyclerViewBookRoom = (RecyclerView) findViewById(R.id.rcv_BookRoomHotelHoChiMinh);
        recyclerViewBookRoom.setHasFixedSize(true);
        recyclerViewBookRoom.setLayoutManager(new LinearLayoutManager(this));

        bookingDataList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("HoTelInHoChiMinh");

        bookingHoTel_inHoChiMinhAdapter = new BookingHoTel_InHoChiMinhAdapter(ListBookRoomHotelInHoChiMinh_Activity.this, bookingDataList);

        recyclerViewBookRoom.setAdapter(bookingHoTel_inHoChiMinhAdapter);

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BookingHoChiMinhData bookingHoChiMinhData = dataSnapshot.getValue(BookingHoChiMinhData.class);
                bookingHoChiMinhData.setHotelName(dataSnapshot.child("hotelName").getValue().toString());
                bookingDataList.add(bookingHoChiMinhData);
                bookingHoTel_inHoChiMinhAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                BookingHoChiMinhData bookingHoChiMinhData = dataSnapshot.getValue(BookingHoChiMinhData.class);
                bookingHoChiMinhData.setHotelName(dataSnapshot.child("hotelName").getValue().toString());
                bookingDataList.remove(bookingHoChiMinhData);
                bookingHoTel_inHoChiMinhAdapter.notifyDataSetChanged();
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

