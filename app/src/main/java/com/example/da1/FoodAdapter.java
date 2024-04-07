package com.example.da1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.da1.models.FoodSP;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter <FoodAdapter.FoodViewHoler>{
    private List<FoodSP> listFood;
    private Context context;
    private RecyclerView recyclerView;



    public void removeItem(int position) {
        listFood.remove(position);
        notifyItemRemoved(position);
    }
    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;

    }


    public FoodAdapter(Context context,List<FoodSP> listFood) {
        this.listFood = new ArrayList<>(listFood);
        this.context = context;

    }

    @NonNull
    @Override
    public FoodViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate((R.layout.sanpham_giohang),parent,false);
        return new  FoodViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHoler holder, int position) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Food");


        FoodSP food = listFood.get(position);

        if (food ==null){
            return;
        }
//        holder.imageFood.setImageResource(food.getImage());
        holder.tvNameFood.setText(food.getName());
        holder.tvPriceFood.setText((String.valueOf(food.getGia()* food.getSoluong())));
        holder.tvSoluong.setText((String.valueOf(food.getSoluong())));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FoodSP.FoodCloud foodCloud = dataSnapshot.getValue(FoodSP.FoodCloud.class);
                    if (foodCloud != null && foodCloud.getNameFood().equals(food.getName())) {
                        String imageUrl = foodCloud.getImageURL();
                        Glide.with(context).load(foodCloud.getImageURL()).into(holder.imageFood); // hiện thị ảnh

                        // Sử dụng imageUrl để hiển thị ảnh trong ứng dụng của bạn

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu cần
            }
        });




    }

    @Override
    public int getItemCount() {
        if (listFood != null) {
            return listFood.size();
        }
        return 0;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (FoodSP item : listFood) {
            totalPrice += item.getGia()*item.getSoluong();
        }
        return totalPrice;
    }
//    public void updateTotalPrice(TextView tvTotalPrice) {
//        int totalPrice = getTotalPrice();
//        tvTotalPrice.setText(String.format("Tổng giá: %d", totalPrice));
//    }


    public class FoodViewHoler extends RecyclerView.ViewHolder{
        final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Food");
        private ImageView imageFood;
        private TextView tvNameFood;

        private TextView tvPriceFood;
        private TextView tvSoluong;
        private Button btnTang,btnGiam;
        private ImageButton btnXoa;
        int giagoc2;
        private FoodAdapter adapter;

        FoodDAO foodDAO ;
        private List<FoodSP> dataList;






        public FoodViewHoler(@NonNull View itemView ) {
            super(itemView);


            imageFood = itemView.findViewById(R.id.imgSP_Giohang);
            tvNameFood =itemView.findViewById(R.id.tvGiohang_nameSP);
            tvPriceFood = itemView.findViewById(R.id.tvGiohang_gia);
            tvSoluong = itemView.findViewById(R.id.tvGiohang_SL);
            btnTang = itemView.findViewById(R.id.btnGiohang_tang);
            btnGiam = itemView.findViewById(R.id.btnGiohang_giam);
            btnXoa = itemView.findViewById(R.id.btnGiohang_xoa);
            dataList = new ArrayList<>();
            foodDAO = new FoodDAO(itemView.getContext());

            Log.e("hol2","");
//            tongtien(giagoc,chuyenInt(tvSoluong.getText().toString()));

            dataList = foodDAO.getDSFOOD();
            FoodAdapter adapter = (FoodAdapter) recyclerView.getAdapter(); // đừng lộn

            btnTang.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Log.e("btnT", tvPriceFood.getText().toString());
                   int Soluong = Integer.parseInt(tvSoluong.getText().toString());
                   if (Soluong >10 ){
                       Toast.makeText(itemView.getContext(), "Số lượng đạt mức tối thiểu", Toast.LENGTH_SHORT).show();
                   }
                   else {
                       Soluong = Soluong+1;
                   }
                   tvSoluong.setText(Soluong+"");
                   foodDAO.suaSoluongFood(tvNameFood.getText().toString(),Soluong);
//                   tongtien(giagoc,Soluong);
                   MyFirebaseListener.getPriceForFood(tvNameFood.getText().toString(), new MyFirebaseListener.OnPriceReceivedListener() {
                       @Override
                       public void onPriceReceived(int price) {
                           int Soluong = Integer.parseInt(tvSoluong.getText().toString());
                          int a = tongtien(price,Soluong);
                          tvPriceFood.setText(a+"");
                       }

                       @Override
                       public void onCancelled(String errorMessage) {

                       }
                   });
                   int position = getAdapterPosition();
                   if (position != RecyclerView.NO_POSITION) {
                       FoodSP foodItem = listFood.get(position);
                       foodItem.setSoluong(Soluong);
                       notifyItemChanged(position);
                   }

                   getTotalPrice();
                   Log.e("gia",getTotalPrice()+"" );


               }
           });
           btnGiam.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   int Soluong = Integer.parseInt(tvSoluong.getText().toString());
                   if (Soluong <=1 ){
                       Toast.makeText(itemView.getContext(), "Số lượng đạt mức tối thiểu", Toast.LENGTH_SHORT).show();
                   }
                   else {
                       Soluong = Soluong-1;
                   }
                   tvSoluong.setText(Soluong+"");// +"" để đổi thành String
                   foodDAO.suaSoluongFood(tvNameFood.getText().toString(),Soluong);
//                   tongtien(giagoc,Soluong);
                   MyFirebaseListener.getPriceForFood(tvNameFood.getText().toString(), new MyFirebaseListener.OnPriceReceivedListener() {
                       @Override
                       public void onPriceReceived(int price) {
                           int Soluong = Integer.parseInt(tvSoluong.getText().toString());
                           int a =tongtien(price,Soluong);
                           tvPriceFood.setText(a+"");

                       }

                       @Override
                       public void onCancelled(String errorMessage) {

                       }
                   });
//
                   int position = getAdapterPosition();
                   if (position != RecyclerView.NO_POSITION) {
                       FoodSP foodItem = listFood.get(position);
                       foodItem.setSoluong(Soluong);
                       notifyItemChanged(position);
                   }
                   adapter.getTotalPrice();
                   Log.e("cao", adapter.getTotalPrice()+"" );


               }



           });
            btnXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        foodDAO.xoaFood(tvNameFood.getText().toString());
                        adapter.removeItem(position);
                    }

                    getTotalPrice();

                }
            });


        }
        public int  tongtien(int price , int sl){
            int a = price*sl;
            return a;
        }
        private void calculateTotalPrice() {
            int totalPrice = 0;
            // Tính tổng giá sản phẩm


//            adapter.getTotalPrice(totalPrice);
        }



    }
}
