package com.example.da1;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class user_detail extends AppCompatActivity {
    private TextView userNameTextView;
    private TextInputEditText ageEditText, phoneEditText;
    private DatabaseReference databaseReference;
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private TextInputEditText edtusername;
    private TextInputEditText edtpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        //"lay bien luu tru username";
        String Username = UserData.getInstance().getUsername(); //
        // Ánh xạ các TextView và EditText
        userNameTextView = findViewById(R.id.user_name_textview);
        ageEditText = findViewById(R.id.age_edittext);
        phoneEditText = findViewById(R.id.phone_edittext);



        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Users").child(Username).addValueEventListener(new ValueEventListener() {
            @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    Long userName = snapshot.getValue(Long.class);
//                    ageEditText.setText(userName);
                if (snapshot.exists()) {
                    UserApp user = snapshot.getValue(UserApp.class);
                    if (user != null) {
                        userNameTextView.setText(user.getUserName());
                        Integer age = user.getTuoi();
                        if (age != null) {
                            ageEditText.setText(String.valueOf(age));
                        } else {
                            ageEditText.setText(""); // hoặc hiển thị giá trị mặc định nếu tuổi không có
                        }
                        phoneEditText.setText(user.getSdt());
                    }
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}