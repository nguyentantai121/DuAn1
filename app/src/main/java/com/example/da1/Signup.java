package com.example.da1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Instant;

public class Signup extends AppCompatActivity {
EditText User_sign,Pass_sign,Pass_sign2;
Button btnSign;
RadioButton rdoNV,rdoQL;
RadioGroup rdoGr;
FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    int lv=0;
    private final String key_users = "Users";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        User_sign = findViewById(R.id.edtUser_sign);
        Pass_sign = findViewById(R.id.edtPass_sign);
        Pass_sign2 = findViewById(R.id.edtPass_sign2);
        btnSign = findViewById(R.id.btnSign);
        rdoNV = findViewById(R.id.rdoNV);
        rdoQL = findViewById(R.id.rdoQL);
        rdoGr = findViewById(R.id.radiogr);

        rdoGr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                    if (checkedId == R.id.rdoNV) {
                        lv =2;
                    } else if (checkedId == R.id.rdoQL) {
                        lv =1;
                    }

            }
        });


        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = User_sign.getText().toString().trim();
                String pass = Pass_sign.getText().toString().trim();
                String pass2 = Pass_sign2.getText().toString().trim();
                if (user.isEmpty() || pass.isEmpty() || pass2.isEmpty() ){
                    Toast.makeText(Signup.this, "vui lòng không để trống", Toast.LENGTH_SHORT).show();
                    return ;
                }
                else if(!pass.equals(pass2)){
                    Toast.makeText(Signup.this, "mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Signup.this, pass+"|"+pass2, Toast.LENGTH_SHORT).show();
                    return;
                }
//                check(user,pass,pass2);

                tao_user(user,pass);
                Intent intent = new Intent(Signup.this,Login.class);
                startActivity(intent);
            }
        });
    }

    public void  check(String user , String pass,String pass2){
        if (user.isEmpty() || pass.isEmpty() || pass2.isEmpty() ){
            Toast.makeText(this, "vui lòng không để trống", Toast.LENGTH_SHORT).show();
            return ;
        }
         else if(!pass.equals(pass2)){
            Toast.makeText(this, "mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, pass+"|"+pass2, Toast.LENGTH_SHORT).show();
            return;
        }
    }
    private void tao_user(String user,String pass){


        //User(tai_khoan,
        //     mat_khau,
        //    tuoi
        //    sdt
        //     role) // 0: user - 1: admin
        myRef.child(key_users).child(user).setValue(new UserApp(user,pass,"null",lv,0))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Signup.this, "Add user thanh cong", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Signup.this, "Add user thai bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}