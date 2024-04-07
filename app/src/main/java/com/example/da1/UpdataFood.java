package com.example.da1;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.da1.models.FoodSP;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UpdataFood extends AppCompatActivity {

    private ImageView image_FoodUpload;
    EditText edtFood_NameUpload,edtFood_PriceUpload;
    private Uri imageUri;
    Button btnUpload;
    int rdo =1;
    RadioButton rdoANVATU,rdoNUOCU;
    final  private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Food");
    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_food);

        btnUpload = findViewById(R.id.btnUpload);
        image_FoodUpload = (ImageView) findViewById(R.id.image_FoodUpload);
        edtFood_NameUpload = (EditText) findViewById(R.id.edtFood_NameUpload);
        edtFood_PriceUpload = (EditText) findViewById(R.id.edtFood_PriceUpload);
        rdoANVATU = (RadioButton) findViewById(R.id.rdoANVAT_Upload);
        rdoNUOCU = (RadioButton) findViewById(R.id.rdoNUOC_Upload);


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            imageUri = data.getData();
                            image_FoodUpload.setImageURI(imageUri);
                        } else {
                            Toast.makeText(UpdataFood.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        image_FoodUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null){
                    uploadToFirebase(imageUri);
                } else  {
                    Toast.makeText(UpdataFood.this, "Please select image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //Outside onCreate
    private void uploadToFirebase(Uri uri){
        String Name = edtFood_NameUpload.getText().toString();
        int price = Integer.parseInt(edtFood_PriceUpload.getText().toString());
        if (rdoNUOCU.isChecked()){
          rdo =2;
        }


        final StorageReference imageReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));

        imageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String key = databaseReference.push().getKey();
                        FoodSP.FoodCloud foodCloud = new FoodSP.FoodCloud(uri.toString(),key,Name,price,rdo);
                        databaseReference.child(key).setValue(foodCloud);
                        Toast.makeText(UpdataFood.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdataFood.this, SanPhamFragment.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdataFood.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private String getFileExtension(Uri fileUri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }
}
