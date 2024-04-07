package com.example.da1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class home2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;
    Trangchu trangchuFragmet = new Trangchu();
    GioHangFragment gioHangFragment = new GioHangFragment();
    DonHangFragment donHangFragment= new DonHangFragment();
    SanPhamFragment sanPhamFragment = new SanPhamFragment();
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    ImageView imageView;
    DrawerLayout drawerLayout;
    int lv;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,trangchuFragmet).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                        int i = item.getItemId();
                        if (i ==R.id.home){
                            getSupportFragmentManager().beginTransaction().replace(R.id.container,trangchuFragmet).commit();
                            return true;
                        } else if (i==R.id.cart) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.container,gioHangFragment).commit();
                            return true;
                        } else if (i==R.id.list) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.container,donHangFragment).commit();
                            return true;
                        }else if (i==R.id.list2) {
                            Intent intent = new Intent(home2.this,UpdataFood.class);
                            startActivity(intent);
//                    getSupportFragmentManager().beginTransaction().replace(R.id.container,updataFood).commit();
                            return true;
                        }



                return false;
            }
        });
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        String Username = UserData.getInstance().getUsername();
        MyFirebaseListener.getLVForUser("Users", Username, new MyFirebaseListener.LoginLVReceivedListener() {
            @Override
            public void LoginLVReceived(int LV) {
               lv = LV;

            }

            @Override
            public void onCancelled(String errorMessage) {

            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (lv ==2 ){
            Toast.makeText(home2.this, "Chức năng không thể sử dụng", Toast.LENGTH_SHORT).show();
        }
        else {
            int itemId = menuItem.getItemId();
            if (itemId == R.id.thongticanhan) {
                Intent intent = new Intent(home2.this,user_detail.class);
                Toast.makeText(home2.this, "thông tin cá nhân mở", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            } else if (itemId == R.id.tknv) {
                Intent intent = new Intent(home2.this, QLNV.class);
                Toast.makeText(home2.this, "Chức năng chưa cập nhật", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            } else if (itemId == R.id.thoat) {
                // Chuyển hướng đến trang đăng nhập
                Intent intent = new Intent(home2.this, Login.class);
                startActivity(intent);
                finish(); // Kết thúc activity hiện tại để ngăn người dùng quay lại từ trang đăng nhập
            } else if (itemId == R.id.lienhe) {
                Toast.makeText(home2.this, "Cài Đặc được chọn", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.danhgia) {
                Toast.makeText(home2.this, "Cài Đặc được chọn", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.cskh) {
                Toast.makeText(home2.this, "Cài Đặc được chọn", Toast.LENGTH_SHORT).show();
            }
        }


        // Đóng drawer khi mục được chọn
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}