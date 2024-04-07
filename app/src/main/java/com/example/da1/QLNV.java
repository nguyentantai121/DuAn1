package com.example.da1;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1.R;
import com.example.da1.UserApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QLNV extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private static List<UserApp> mUserList;
    private DatabaseReference mDatabaseReference;
    private RecyclerView.Adapter mAdapter;
    TextView title;
    int lv=0;
    private final String key_users = "Users";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlnv);

        // Khởi tạo database reference
        myRef = FirebaseDatabase.getInstance().getReference();

        // Tìm FAB bằng ID

        FloatingActionButton fab = findViewById(R.id.add_product_button);
        fab.setVisibility(View.VISIBLE);

        // Thiết lập sự kiện onClick cho FAB
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Gọi phương thức hiển thị dialog thêm tài khoản
                showAddAccountDialog();    // Đóng BottomSheetDialog


            }
        });




        // Khởi tạo RecyclerView và danh sách dữ liệu
        mRecyclerView = findViewById(R.id.rcvTkNV);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUserList = new ArrayList<>();
        mAdapter = new RecyclerView.Adapter<UserViewHolder>() {
            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
                return new UserViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
                UserApp user = mUserList.get(position);
                holder.bind(user);
                // Sự kiện khi người dùng nhấn vào nút "update"
                holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Hiển thị Dialog hoặc BottomSheetDialog và truyền dữ liệu tương ứng của mục đó
                        showUpdateDialog(user);

                    }
                });
            }

            // Phương thức để hiển thị BottomSheetDialog
            private void showUpdateDialog(UserApp user) {
                // Tạo một BottomSheetDialog mới
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(QLNV.this);
                // Inflate layout cho BottomSheetDialog
                View dialogView = getLayoutInflater().inflate(R.layout.activity_update_user, null);
                bottomSheetDialog.setContentView(dialogView);

                // Tìm kiếm các thành phần trong layout của BottomSheetDialog
                EditText editTextUserName = dialogView.findViewById(R.id.edit_text_username);
                EditText editTextPassword = dialogView.findViewById(R.id.edit_text_password);
                EditText editTextSdt = dialogView.findViewById(R.id.edit_text_sdt);
                EditText editTextLv = dialogView.findViewById(R.id.edit_text_lv);
                EditText editTextTuoi = dialogView.findViewById(R.id.edit_text_tuoi);
                Button btnSave = dialogView.findViewById(R.id.btn_save);
                Button btncancal = dialogView.findViewById(R.id.btn_cancel);


                // Đặt giá trị ban đầu cho các EditText dựa trên dữ liệu của user
                editTextUserName.setText(user.getUserName());
                editTextPassword.setText(user.getPassword());
                editTextSdt.setText(user.getSdt());
                editTextLv.setText(String.valueOf(user.getLv()));
                editTextTuoi.setText(String.valueOf(user.getTuoi()));

                btncancal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Đóng BottomSheetDialog khi hủy
                        Toast.makeText(getApplicationContext(), "Hủy", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                });
                // Sự kiện lưu khi người dùng nhấn vào nút "Save"
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Lấy dữ liệu từ các EditText
                        String newUserName = editTextUserName.getText().toString();
                        String newPassword = editTextPassword.getText().toString();
                        String newSdt = editTextSdt.getText().toString();
                        int newLv = Integer.parseInt(editTextLv.getText().toString());
                        int newTuoi = Integer.parseInt(editTextTuoi.getText().toString());

                        // Cập nhật thông tin người dùng trong Firebase
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUserName());
                        userRef.child("userName").setValue(newUserName);
                        userRef.child("password").setValue(newPassword);
                        userRef.child("sdt").setValue(newSdt);
                        userRef.child("lv").setValue(newLv);
                        Task<Void> tuoi = userRef.child("tuoi").setValue(newTuoi)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        // Hiển thị thông báo thành công
                                        Toast.makeText(getApplicationContext(), "Thông tin đã được cập nhật", Toast.LENGTH_SHORT).show();
                                        // Đóng BottomSheetDialog sau khi lưu
                                        bottomSheetDialog.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Hiển thị thông báo thất bại
                                        Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi khi cập nhật thông tin", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

                // Hiển thị BottomSheetDialog
                bottomSheetDialog.show();
            }


            @Override
            public int getItemCount() {
                return mUserList.size();
            }
        };
        mRecyclerView.setAdapter(mAdapter);

        // Tham chiếu đến cơ sở dữ liệu Firebase
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        // Lắng nghe sự thay đổi dữ liệu trên Firebase
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Xóa dữ liệu cũ
                mUserList.clear();
                // Lặp qua mỗi child (đối tượng) trong dataSnapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Lấy dữ liệu của đối tượng và thêm vào danh sách
                    UserApp user = snapshot.getValue(UserApp.class);
                    // Kiểm tra nếu tên người dùng không phải là "admin"
                    if (!user.getUserName().equals("admin1")) {
                        mUserList.add(user);
                    }
                }
                // Cập nhật giao diện
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });
    }

    // ViewHolder cho mỗi item trong RecyclerView
    private static class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView txtname, txttuoi, txtpass, txtlv, txtsdt;
        private ImageButton btnDelete, btnUpdate;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtname = itemView.findViewById(R.id.txtname);
            txtpass = itemView.findViewById(R.id.txtpass);
            txtlv = itemView.findViewById(R.id.txtlv);
            txtsdt = itemView.findViewById(R.id.txtsdt);
            txttuoi = itemView.findViewById(R.id.txttuoi);
            btnDelete = itemView.findViewById(R.id.delete_icon);
            btnUpdate = itemView.findViewById(R.id.update_icon);

            // Đặt sự kiện click cho nút "Delete"
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        UserApp user = mUserList.get(position);
                        // Xác định DatabaseReference của người dùng cần xóa
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUserName());
                        // Xóa người dùng khỏi Firebase
                        userRef.removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Hiển thị thông báo thành công
                                        Toast.makeText(itemView.getContext(), "Người dùng đã được xóa", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Hiển thị thông báo thất bại
                                        Toast.makeText(itemView.getContext(), "Đã xảy ra lỗi khi xóa người dùng", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            });
        }
        public void bind(UserApp user) {
            txtname.setText(user.getUserName());
            txtsdt.setText(user.getSdt());
            txttuoi.setText(String.valueOf(user.getTuoi()));
            txtpass.setText(user.getPassword());
            txtlv.setText(String.valueOf(user.getLv()));
        }
    }
    // Phương thức để hiển thị dialog thêm tài khoản
    private void showAddAccountDialog() {
        // Tạo một BottomSheetDialog mới
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(QLNV.this);
        // Inflate layout cho BottomSheetDialog
        View dialogView = getLayoutInflater().inflate(R.layout.activity_update_user, null);
        bottomSheetDialog.setContentView(dialogView);

        // Tìm kiếm các thành phần trong layout của BottomSheetDialog
        EditText edtUserName = dialogView.findViewById(R.id.edit_text_username);
        EditText edtPassword = dialogView.findViewById(R.id.edit_text_password);
        EditText edtSdt = dialogView.findViewById(R.id.edit_text_sdt);
        EditText edtLv = dialogView.findViewById(R.id.edit_text_lv);
        EditText edtTuoi = dialogView.findViewById(R.id.edit_text_tuoi);
        Button btnSave = dialogView.findViewById(R.id.btn_save);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

        // Sự kiện lưu khi người dùng nhấn vào nút "Save"
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ EditText
                String userName = edtUserName.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String sdt = edtSdt.getText().toString().trim();
                String lvStr = edtLv.getText().toString().trim();
                String tuoiStr = edtTuoi.getText().toString().trim();

                // Kiểm tra xem các trường dữ liệu có rỗng hay không
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password) || TextUtils.isEmpty(sdt) || TextUtils.isEmpty(lvStr) || TextUtils.isEmpty(tuoiStr)) {
                    // Nếu có trường dữ liệu nào đó rỗng, hiển thị thông báo lỗi
                    Toast.makeText(QLNV.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return; // Dừng xử lý tiếp theo nếu có trường rỗng
                }

                // Chuyển đổi chuỗi lvStr và tuoiStr thành số nguyên
                int lv, tuoi;
                try {
                    lv = Integer.parseInt(lvStr);
                    tuoi = Integer.parseInt(tuoiStr);
                } catch (NumberFormatException e) {
                    // Xử lý nếu không thể chuyển đổi thành số nguyên
                    Toast.makeText(QLNV.this, "Vui lòng nhập số cho tuổi và cấp bậc!", Toast.LENGTH_SHORT).show();
                    return; // Dừng xử lý tiếp theo nếu có lỗi
                }

                // Gọi phương thức tạo user và đóng BottomSheetDialog
                tao_user(userName, password, sdt, lv, tuoi);
                bottomSheetDialog.dismiss();
            }
        });


        // Sự kiện hủy khi người dùng nhấn vào nút "Cancel"
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đóng BottomSheetDialog
                bottomSheetDialog.dismiss();
            }
        });

        // Hiển thị BottomSheetDialog
        bottomSheetDialog.show();

    }
    private void tao_user(String userName,String password,String sdt,int lv , int tuoi){


        //User(tai_khoan,
        //     mat_khau,
        //    tuoi
        //    sdt
        //     role) // 0: user - 1: admin
        myRef.child(key_users).child(userName).setValue(new UserApp(userName,password,sdt,lv,tuoi))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(QLNV.this, "Add user thanh cong", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(QLNV.this, "Add user thai bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}