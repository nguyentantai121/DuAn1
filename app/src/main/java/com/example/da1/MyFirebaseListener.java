package com.example.da1;


import android.util.Log;

import com.example.da1.models.FoodSP;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

public class MyFirebaseListener {

    public interface OnPriceReceivedListener {
        void onPriceReceived(int price);
        void onCancelled(String errorMessage);
    }
    public interface OnIDReceivedListener {
        void onIDReceived(String ID);
        void onCancelled(String errorMessage);
    }public interface LoginLVReceivedListener {
        void LoginLVReceived(int LV);
        void onCancelled(String errorMessage);
    }


    public static void getPriceForFood(String foodName, final OnPriceReceivedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Food");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FoodSP.FoodCloud foodCloud = dataSnapshot.getValue(FoodSP.FoodCloud.class);
                    if (foodCloud != null && foodCloud.getNameFood().equals(foodName)) {
                        int price = foodCloud.getGiaFood();
                        listener.onPriceReceived(price);
                        return;
                    }
                }
                // Không tìm thấy món ăn
                listener.onCancelled("Không tìm thấy món ăn trong cơ sở dữ liệu");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi
                listener.onCancelled(error.getMessage());
            }
        });
    }
    public static void getIDForFood(String key,String name, final OnIDReceivedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference(key);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FoodSP.FoodCloud foodCloud = dataSnapshot.getValue(FoodSP.FoodCloud.class);
                    if (foodCloud != null && foodCloud.getNameFood().equals(name)) {
                        String ID  = foodCloud.getIdFood();
                        Log.e("ID", ID+"" );
                        listener.onIDReceived(ID);
                        return;
                    }
                }

                listener.onCancelled("Không tìm thấy ID trong cơ sở dữ liệu");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi
                listener.onCancelled(error.getMessage());
            }
        });
    }
    public static void getLVForUser(String key,String name, final LoginLVReceivedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference(key);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserApp userApp = dataSnapshot.getValue(UserApp.class);
                    if (userApp != null && userApp.getUserName().equals(name)) {
                        int LV  = userApp.getLv();
                        Log.e("ID", LV+"" );
                        listener.LoginLVReceived(LV);
                        return;
                    }
                }

                listener.onCancelled("Không tìm thấy LV trong cơ sở dữ liệu");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi
                listener.onCancelled(error.getMessage());
            }
        });
    }
}
