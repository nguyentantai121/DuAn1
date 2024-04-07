package com.example.da1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

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

/**
 * A fragment representing a list of Items.
 */
public class SanPhamFragment extends Fragment {
    RecyclerView rcvFood;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Food");
    private ArrayList<FoodSP.FoodCloud> dataList;
    private FoodAdapterCloud adapterCloud;
    ImageButton btnAddUP;
    private Activity hostingActivity;


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_san_pham_list, container, false);




        rcvFood = (RecyclerView) view.findViewById(R.id.rcvSanpham);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),2);
        btnAddUP = view.findViewById(R.id.btnAddUP);
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
        btnAddUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hostingActivity, UpdataFood.class);
                hostingActivity.startActivity(intent);
            }
        });

        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            hostingActivity = (Activity) context;
        }
    }
}