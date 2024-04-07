package com.example.da1;

import static com.example.da1.Chuyendoi.chuyenInt;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.da1.models.FoodSP;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapterCloud extends RecyclerView.Adapter<FoodAdapterCloud.MyViewHolder> {

    private  List<FoodSP.FoodCloud> dataList;
    private Context context;

    public FoodAdapterCloud(Context context, List<FoodSP.FoodCloud> dataList) {

        this.context = context;
        this.dataList = new ArrayList<>(dataList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getImageURL()).into(holder.imgFood); // hiện thị ảnh
        holder.nameFood.setText(dataList.get(position).getNameFood());
        holder.giaFood.setText(dataList.get(position).getGiaFood()+"VNĐ");

    }
    public void setData(List<FoodSP.FoodCloud> newData) {
        dataList.clear();
        dataList.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        DonHangFragment fragment = new DonHangFragment();

        ImageView imgFood;
        TextView nameFood;
        TextView giaFood;
        Button AddSp;
        private FoodDAO foodDAO ;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.img_food);
            nameFood = itemView.findViewById(R.id.name_food);
            giaFood = itemView.findViewById(R.id.gia_food);
            AddSp = itemView.findViewById(R.id.btn_addSP);
            AddSp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v ) {
                    openDiglog(Gravity.CENTER,nameFood.getText().toString())  ;
                    Toast.makeText(itemView.getContext(), "Nhấn thành công", Toast.LENGTH_SHORT).show();
                }
            });

        }
        private  void openDiglog(int gravity ,String name)    {
//            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

            final Dialog dialog = new Dialog(itemView.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_diglog_sp);
            Window window = dialog.getWindow();
            if (window == null){
                return;
            }
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams windowA = window.getAttributes();
            windowA.gravity =gravity;
            window.setAttributes(windowA);
                dialog.setCancelable(true);
                TextView nameFood =dialog.findViewById(R.id.name_DiglogCart);
            EditText edt_ban = dialog.findViewById(R.id.edt_ban);
            EditText edt_soLuong = dialog.findViewById(R.id.edt_soLuongCart);
            Button tangSl = dialog.findViewById(R.id.btn_tangCart);
            Button  giamSl = dialog.findViewById(R.id.btn_giamCart);
            Button  addCart = dialog.findViewById(R.id.btn_addCart);
            nameFood.setText(name);
            //Tăng SL

            giamSl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int soLuong = Integer.parseInt(edt_soLuong.getText().toString());

                    if (soLuong <=1 ){
                        Toast.makeText(itemView.getContext(), "Số lượng đạt mức tối thiểu", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        soLuong = soLuong-1;
                    }
                    edt_soLuong.setText(soLuong+"");
                }
            });
            //Giảm SL

            tangSl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int soLuong = Integer.parseInt(edt_soLuong.getText().toString());

                    if (soLuong >10 ){
                        Toast.makeText(itemView.getContext(), "Số lượng đạt mức tối thiểu", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        soLuong = soLuong+1;
                    }
                    edt_soLuong.setText(soLuong+"");
                }
            });


            addCart.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                   String name = nameFood.getText().toString();
                   int gia = chuyenInt(giaFood.getText().toString());
                   int soluong = chuyenInt(edt_soLuong.getText().toString());
                    FoodDAO foodDAO = new FoodDAO(itemView.getContext());
//                   boolean a = foodDAO.themItemFood(name,gia,1,soluong);
                    boolean a = foodDAO.themItemFood(new FoodSP(name,gia,soluong));

                    Log.d("DB",name+"-"+gia+"-"+soluong );
                    if (a){
                        Log.d("DB", "chèn thành công ");
                    }else {
                        Log.d("DB", "chèn không thành công ");

                    }

                }


            });
            dialog.show();
        }
    }

}

