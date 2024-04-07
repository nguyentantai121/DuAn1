package com.example.da1;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity {
    private TextInputEditText edtusername;
    private TextInputEditText edtpassword;
    private Button signInButton;
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

//ánh xạ
            edtusername = findViewById(R.id.edtnhapuser);
            edtpassword = findViewById(R.id.edtnhappass);
            signInButton = findViewById(R.id.buttonSignIn);
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = edtusername.getText().toString();
                    String password = edtpassword.getText().toString();

                    if (username.isEmpty() && password.isEmpty()) {
                        Toast.makeText(Login.this, "Vui lòng nhập tên người dùng và mật khẩu", Toast.LENGTH_SHORT).show();
                    } else if (username.isEmpty()) {
                        Toast.makeText(Login.this, "Vui lòng nhập tên người dùng", Toast.LENGTH_SHORT).show();
                    } else if (password.isEmpty()) {
                        Toast.makeText(Login.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    } else if (password.length() < 3) {
                        Toast.makeText(Login.this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
                    }
                    // Lưu giá trị username vào UserData (Singleton)

                    UserData.getInstance().setUsername(username);
                    // Lấy đối tượng DatabaseReference

                    DatabaseReference usersRef = rootRef.child("Users");

// Tên người dùng cần đọc dữ liệu


// Đọc dữ liệu từ Firebase
                    usersRef.child(username).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // Dữ liệu đã thay đổi
                            if (snapshot.exists()) {

                                String passwordDB = snapshot.child("password").getValue(String.class);
                                if (Objects.equals(passwordDB, password)){
                                    Intent intent = new Intent(Login.this,home2.class);
                                    startActivity(intent);
                                    Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(Login.this, "Tài Khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                                    Log.w("Firebase", "mật khẩu DB " +passwordDB);
                                    Log.w("Firebase", "mật khẩu nhập " +password);



                                }

                            } else {
                                // Không tìm thấy người dùng
                                Log.w("Firebase", "Không tìm thấy người dùng" +username);
                                Toast.makeText(Login.this, "Tài Khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Xử lý lỗi nếu có
                            Log.w("Firebase", "Lỗi đọc dữ liệu", error.toException());
                        }
                    });
                    checkUser();
                }


            });
            return insets;
        });
    }
    public void checkUser(){

    }
}
