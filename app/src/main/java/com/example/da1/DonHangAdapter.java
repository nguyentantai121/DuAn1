package com.example.da1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1.models.FoodSP;

import java.util.List;

public class DonHangAdapter extends  RecyclerView.Adapter <DonHangAdapter.FoodViewHoler>{
    private List<FoodSP> listFood;
    private Context context;
    private RecyclerView recyclerView;


    @NonNull
    @Override
    public DonHangAdapter.FoodViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangAdapter.FoodViewHoler holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }

    public class FoodViewHoler extends RecyclerView.ViewHolder {

        public FoodViewHoler(@NonNull View itemView) {
            super(itemView);
        }
    }
}
