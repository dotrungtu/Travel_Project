package com.example.travel_project.NetWork;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class TourWasBookedDelete {
        public static Task<Void> deleteBookTour(String placeName)
        {
            Task<Void> task = FirebaseDatabase.getInstance().getReference("Tour")
                    .child(placeName)
                    .setValue(null);
            return task;
        }
}

