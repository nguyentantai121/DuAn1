package com.example.da1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1.models.FoodSP;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Trangchu extends Fragment {

    RecyclerView rcvFood;
    Button btnAnvat,btnNuoc,btnAddSP;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Food");
    private ArrayList<FoodSP.FoodCloud> dataList;
    private FoodAdapterCloud adapterCloud;

    View mView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView= inflater.inflate(R.layout.fragment_trangchu, container, false);
        btnAnvat = (Button) mView.findViewById(R.id.btn_Anvat);
        btnAddSP = (Button) mView.findViewById(R.id.btn_addSP);

        btnNuoc= (Button) mView.findViewById(R.id.btn_Nuoc);
        rcvFood = (RecyclerView) mView.findViewById(R.id.rcvFood);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),2);
        rcvFood.setLayoutManager(gridLayoutManager);

        dataList = new ArrayList<>();
        adapterCloud = new FoodAdapterCloud(requireContext(), dataList);
        rcvFood.setAdapter(adapterCloud);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FoodSP.FoodCloud foodCloud = dataSnapshot.getValue(FoodSP.FoodCloud.class);
                    dataList.add(foodCloud);
                }
                adapterCloud.setData(dataList);
                adapterCloud.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnNuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProductsByType(2);
            }
        });

        btnAnvat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProductsByType(1);
            }
        });
        return mView;
    }
    private void showProductsByType(int type) {
        List<FoodSP.FoodCloud> filteredList = new ArrayList<>();
        for (FoodSP.FoodCloud food : dataList) {
            if (food.getLoaiFood() == type) {
                filteredList.add(food);
            }
        }
        adapterCloud.setData(filteredList);
        adapterCloud.notifyDataSetChanged();
    }
}