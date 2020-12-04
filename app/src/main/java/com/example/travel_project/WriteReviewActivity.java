//package com.example.travel_project;
//
//import android.support.annotation.NonNull;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.RatingBar;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.HashMap;
//
//public class WriteReviewActivity extends AppCompatActivity {
//
//    private ImageButton backBtn;
//    private RatingBar ratingBar;
//    private EditText edtReview;
//    private FloatingActionButton btnSubmit;
//
//    FirebaseAuth firebaseAuth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_write_review);
//
//        backBtn = findViewById(R.id.btn_BackReview);
//        ratingBar = findViewById(R.id.ratingBar);
//        edtReview = findViewById(R.id.edt_Review);
//        btnSubmit = findViewById(R.id.btn_Submit);
//
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        loadMyReview();
//
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                inputData();
//            }
//        });
//
//    }
//
//    private void loadMyReview() {
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
//        reference.child("Ratings").child(firebaseAuth.getUid())
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.exists()){
//                            String uid = "" + dataSnapshot.child("uid").getValue();
//                            String ratings = "" + dataSnapshot.child("ratings").getValue();
//                            String review = "" +dataSnapshot.child("review").getValue();
//                            String timestamp = ""+dataSnapshot.child("timestamp").getValue();
//
//                            float myRating = Float.parseFloat(ratings);
//                            ratingBar.setRating(myRating);
//                            edtReview.setText(review);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//    }
//
//    private void inputData() {
//        String ratings = ""+ratingBar.getRating();
//        String review = edtReview.getText().toString().trim();
//
//        String timestamp = ""+System.currentTimeMillis();
//
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("ratings", ""+ ratings);
//        hashMap.put("review", ""+ review);
//        hashMap.put("timestamp", ""+ timestamp);
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
//        reference.child("Ratings").child(firebaseAuth.getUid()).updateChildren(hashMap)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(WriteReviewActivity.this, "Phản hồi của bạn đã được gởi", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(WriteReviewActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//}
