package com.example.da1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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


public class DonHangFragment extends Fragment {
    RecyclerView recyD;
    FoodAdapter foodAdapter;
    FoodDAO foodDAO;
    private List<FoodSP> dataList;
    View mview;

    TextView tvName_Donhang,tvNgay_DonHang,tvTrangThai_DonHang;

    private final String key_Donhang = "DonHang";
    private final String key_Fooditem = "Fooditem";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         mview = inflater.inflate(R.layout.fragment_don_hang, container, false);
        tvName_Donhang =  mview.findViewById(R.id.tvName_Donhang);
        tvNgay_DonHang =  mview.findViewById(R.id.tvNgay_DonHang);
        tvTrangThai_DonHang =  mview.findViewById(R.id.tvTrangThai_DonHang);

        recyD =  mview.findViewById(R.id.rycDonHangD);
        dataList = new ArrayList<>();
        foodDAO = new FoodDAO(requireContext());
        dataList = foodDAO.getDSFOOD();
        foodAdapter = new FoodAdapter(requireContext() ,dataList);
        foodAdapter.setRecyclerView(recyD);
        recyD.setAdapter(foodAdapter);
        recyD.setLayoutManager(new LinearLayoutManager(requireContext()));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FoodSP.FoodCloud foodCloud = dataSnapshot.getValue(FoodSP.FoodCloud.class);
//                    dataList.add(foodCloud);
                }

                foodAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return mview;
    }
}