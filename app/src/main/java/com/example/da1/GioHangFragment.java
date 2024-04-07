package com.example.da1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1.models.FoodSP;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class GioHangFragment extends Fragment {
    RecyclerView rcyGiohang;
    FoodAdapter foodAdapter;
    FoodDAO foodDAO;
    private List<FoodSP> dataList;
    View mview;
    EditText edit_ghichu;
    TextView tv_Tongtien;
    Button btn_Thanhtoan;
    private final String key_Donhang = "DonHang";
    private final String key_Fooditem = "Fooditem";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview =inflater.inflate(R.layout.fragment_gio_hang, container, false);
        edit_ghichu =  mview.findViewById(R.id.edit_ghichu);
        tv_Tongtien =  mview.findViewById(R.id.tv_Tongtien);
        btn_Thanhtoan =  mview.findViewById(R.id.btn_Thanhtoan);

        rcyGiohang =  mview.findViewById(R.id.rycGiohang);
        dataList = new ArrayList<>();
        foodDAO = new FoodDAO(requireContext());
        dataList = foodDAO.getDSFOOD();
        foodAdapter = new FoodAdapter(requireContext() ,dataList);
        foodAdapter.setRecyclerView(rcyGiohang);
        rcyGiohang.setAdapter(foodAdapter);
        rcyGiohang.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (dataList != null && !dataList.isEmpty()) {
            Log.e("listfood", "Không rỗng: " );
        } else {
            Log.e("listfood", " rỗng: " );
        }
        int totalPrice = foodAdapter.getTotalPrice();
//        foodAdapter.updateTotalPrice(tv_Tongtien); // Cập nhật lại tổng giá ban đầu
        tv_Tongtien.setText(String.format("Tổng giá: %d ", totalPrice));
//        for (FoodItem item : dataList) {
//            totalPrice += item.getPrice() * item.getQuantity();
//        }

        btn_Thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date dNow = new Date( );
                SimpleDateFormat ngay =
                        new SimpleDateFormat ("E yyyy.MM.dd");
                SimpleDateFormat gio =
                        new SimpleDateFormat ("hh:mm");
                String key = myRef.push().getKey();

//                private void taoDonhang(int ban, int tongtien, String idDongHang, String ngay, String gio, String ghiChu){

                    taoDonhang(1,totalPrice,key,ngay.format(dNow),gio.format(dNow),edit_ghichu.getText().toString());
                for (FoodSP ignored : dataList) {
//                    private void taoFooditem(String idDonHang, String idFood, int soLuong){
                    MyFirebaseListener.getIDForFood("Food", ignored.getName(), new MyFirebaseListener.OnIDReceivedListener() {
                        @Override
                        public void onIDReceived(String ID) {
                            taoOrderoitem(key,ID,ignored.getSoluong());
                        }

                        @Override
                        public void onCancelled(String errorMessage) {

                        }
                    });


                    }
            }
        });

        return mview;
    }
    private void taoDonhang(int ban, int tongtien, String idDongHang, String ngay, String gio,String ghiChu ){
//         DonHangCloud(int ban, int tongtien, String idDongHang, String ngay, String gio)
        myRef.child(key_Donhang).child(idDongHang).setValue(new FoodSP.DonHangCloud(1,tongtien,idDongHang,ngay,gio,ghiChu,0))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(requireContext(), "Add ĐƠn HAng", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(requireContext(), "Add ĐƠn HAng thai bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void taoOrderoitem(String idDonHang, String idFood, int soLuong){
//    public FoodItemCloud(String idDonHang, String idFood, int soLuong) {
        myRef.child(key_Fooditem).child(idFood).setValue(new Orderitem(idDonHang,idFood,soLuong))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(requireContext(), "Add ĐƠn HAng", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(requireContext(), "Add ĐƠn HAng thai bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}