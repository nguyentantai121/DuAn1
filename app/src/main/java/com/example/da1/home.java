package com.example.da1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

public class home extends AppCompatActivity {
    Toolbar toolbartop,toolbarend;
    RecyclerView rcvFood;
    Button btnAnvat,btnNuoc;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Food");
    private ArrayList<FoodSP.FoodCloud> dataList;
    private FoodAdapterCloud adapterCloud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnAnvat = (Button) findViewById(R.id.btn_Anvat);
        btnNuoc= (Button) findViewById(R.id.btn_Nuoc);
        rcvFood = (RecyclerView) findViewById(R.id.rcvFood);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        rcvFood.setLayoutManager(gridLayoutManager);
//        FoodAdapter adapter = new FoodAdapter(getListFood());
//        rcvFood.setAdapter(adapter);
        dataList = new ArrayList<>();
        adapterCloud = new FoodAdapterCloud(this, dataList);
        rcvFood.setAdapter(adapterCloud);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FoodSP.FoodCloud foodCloud = dataSnapshot.getValue(FoodSP.FoodCloud.class);
                    dataList.add(foodCloud);
                }
                adapterCloud.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
//    private List<Food> getListFood(){
//        List<Food> list_ANVAT = new ArrayList<>();
//        list_ANVAT.add(new Food(R.drawable.banhtrangtron,"bánh tráng trộn",20000,Food.TYPE_ANVAT, soluong));
//        list_ANVAT.add(new Food(R.drawable.banhtrangtron,"bánh tráng trộn",20000,Food.TYPE_ANVAT, soluong));
//        list_ANVAT.add(new Food(R.drawable.banhtrangtron,"bánh tráng trộn",20000,Food.TYPE_ANVAT, soluong));
//        list_ANVAT.add(new Food(R.drawable.banhtrangtron,"bánh tráng trộn",20000,Food.TYPE_ANVAT, soluong));
//
//
//        return list_ANVAT;
//    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@androidx.annotation.NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cart){
            Intent intent = new Intent(home.this,UpdataFood.class);
            startActivity(intent);
            Toast.makeText(this, "Food", Toast.LENGTH_SHORT).show();

        }

                return super.onOptionsItemSelected(item);

    }

}