package com.example.travel_project;

import android.content.Intent;
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

import com.example.travel_project.Adapter.RecentsAdapter;
import com.example.travel_project.Adapter.TourAdapter;
import com.example.travel_project.Model.RecentsData;
import com.example.travel_project.Model.TourData;
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


public class Home_Fragment extends Fragment {
    RecyclerView recentRecycler, tourRecycler;
    RecentsAdapter recentsAdapter;
    TourAdapter tourAdapter;
    ImageView ivImageUser;
    DatabaseReference databaseReference;
    List<RecentsData> recentsDataList = new ArrayList<>();
    List<TourData> tourDataList = new ArrayList<>();
    EditText edtSearch;
    TextView tvTenUser, tvEmailUser;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");
        ivImageUser = (ImageView) view.findViewById(R.id.iv_ImageUser);

        edtSearch = (EditText) view.findViewById(R.id.edtSearch);

        tvTenUser = (TextView) view.findViewById(R.id.textView);
        tvEmailUser = (TextView) view.findViewById(R.id.textView2);
        //ImageView title user
        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //check until required data get

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //get data
                    String name = "" + ds.child("name").getValue();
                    String email = "" + ds.child("email").getValue();
                    String image = "" + ds.child("image").getValue();

                    //set data
                    tvEmailUser.setText(email);
                    tvTenUser.setText(name);

                    try {
                        Picasso.get()
                                .load(image)
                                .transform(new CircleTransform())
                                .into(ivImageUser);
                    } catch (Exception e) {
                        Picasso.get()
                                .load(image)
                                .transform(new CircleTransform())
                                .into(ivImageUser);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Now here we will add some dummy data in our model class
        //City recent list
        recentsDataList = new ArrayList<>();

        recentRecycler = (RecyclerView) view.findViewById(R.id.recent_recycler);

        RecyclerView.LayoutManager layoutManagerRecent = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recentRecycler.setLayoutManager(layoutManagerRecent);
        recentsAdapter = new RecentsAdapter(getContext());
        recentRecycler.setAdapter(recentsAdapter);

        DatabaseReference databaseReferenceRecent = FirebaseDatabase.getInstance().getReference("City");
        databaseReferenceRecent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    RecentsData recentsData = dataSnapshot1.getValue(RecentsData.class);
                    recentsDataList.add(recentsData);
                }
                handleDataRecents();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Now here we will add some dummy data in our model class
        //Top Place list
        tourDataList = new ArrayList<>();

        tourRecycler = (RecyclerView) view.findViewById(R.id.top_places_recycler);

        RecyclerView.LayoutManager layoutManagerTopPlace = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        tourRecycler.setLayoutManager(layoutManagerTopPlace);
        tourAdapter = new com.example.travel_project.Adapter.TourAdapter(getContext(), tourDataList);
        tourRecycler.setAdapter(tourAdapter);

        DatabaseReference databaseReferenceTopPlace = FirebaseDatabase.getInstance().getReference("City");
        databaseReferenceTopPlace.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tourDataList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    TourData tourData = dataSnapshot1.getValue(TourData.class);
                    tourDataList.add(tourData);
                }
                tourAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handleDataRecents();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void handleDataRecents() {
        Log.e("Error", "THISSS");
        String searchText = edtSearch.getText().toString().toLowerCase();
        if (recentsDataList.isEmpty())
            return;
        List<RecentsData> recentsData = recentsDataList.stream()
                .filter(p -> {
                    if (p.getPlaceName() == null || p.getPlaceName().isEmpty()) return false;
                    return p.getPlaceName().toLowerCase().contains(searchText);
                }).collect(Collectors.toList());
        recentsAdapter.setData(recentsData);
    }
}

