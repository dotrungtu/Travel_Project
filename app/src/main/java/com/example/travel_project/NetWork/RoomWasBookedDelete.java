package com.example.travel_project.NetWork;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class RoomWasBookedDelete {
        public static Task<Void> deleteRoom(String hotelName)
        {
            Task<Void> task = FirebaseDatabase.getInstance().getReference("HotelWasBooked")
                    .child(hotelName)
                    .setValue(null);
            return task;
        }
}

