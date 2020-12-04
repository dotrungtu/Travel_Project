package com.example.travel_project;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travel_project.Model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Register_Activity extends AppCompatActivity {

    EditText edtEmail, edtPassword, edtName, edtPhone, edtAddress;
    Button btnRegister;
    TextView tvHaveAccount;
    private static final int PICK_IMAGE_REQUEST = 1;
    Uri imageUri;
    StorageReference uploadingFileRef;
    StorageReference storageReference;
    StorageTask uploadTask;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Trở lại");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        edtEmail = findViewById(R.id.edt_EmailForRegister);
        edtPassword = findViewById(R.id.edt_PasswordForRegister);
        edtName = findViewById(R.id.edt_NameForRegister);
        edtPhone = findViewById(R.id.edt_PhoneNumberForRegister);
        edtAddress = findViewById(R.id.edt_AddressForRegister);

        btnRegister = findViewById(R.id.btn_ForRegister);
        tvHaveAccount = findViewById(R.id.tv_HaveAccount);

        firebaseAuth = FirebaseAuth.getInstance();

        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Vui lòng đợi...");

        btnRegister.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               //Truyền dữ liệu vào input id, email
                                               String email = edtEmail.getText().toString().trim();
                                               String password = edtPassword.getText().toString().trim();
                                               //Bắt lỗi Email
                                               if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                                   edtEmail.setError("Email không hợp lệ!");
                                                   edtEmail.setFocusable(true);
                                               }
                                               //Bắt lỗi Password
                                               else if (password.length() < 6) {
                                                   edtPassword.setError("Password phải chứa ít nhất 6 kí tự!");
                                                   edtPassword.setFocusable(true);
                                               } else {
                                                   dangkyKH(email, password); //lệnh đăng ký user
                                               }
                                           }
                                       }
        );

        tvHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register_Activity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void dangkyKH(String email, String password) {
        alertDialogBuilder.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            alertDialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    dialog.dismiss();
                                }
                            });
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            //Get user Email and ID from Auth

                            String email = user.getEmail();
                            String uid = user.getUid();
                            String name = edtName.getText().toString().trim();
                            String phoneNumber = edtPhone.getText().toString().trim();
                            String address = edtAddress.getText().toString().trim();
                            //When user is registered store user info in firebase realtime database too
                            //using Hashmap
                            HashMap<Object, String> hashMap = new HashMap<>();
                            //put info in hashmap
                            hashMap.put("email", email);
                            hashMap.put("uid", uid); //will add later (e.g.editprofile)
                            hashMap.put("name", name);//will add later (e.g.editprofile)
                            hashMap.put("phone", phoneNumber);//will add later (e.g.editprofile)
                            hashMap.put("image", "");//will add later (e.g.editprofile)
                            hashMap.put("address", address);//will add later (e.g.editprofile)
                            //firebase database instance
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            //path to store user named "User"
                            DatabaseReference databaseReference = firebaseDatabase.getReference("User");
                            //put data within hashmap in database
                            databaseReference.child(uid).setValue(hashMap);

                            Toast.makeText(Register_Activity.this, "Xin chào " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register_Activity.this, Login_Activity.class));
                            finish();
                        } else {
                            alertDialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    dialog.dismiss();
                                }
                            });
                            Toast.makeText(Register_Activity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                alertDialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
                Toast.makeText(Register_Activity.this, " " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();//câu lệnh trở về activity trước
        return super.onSupportNavigateUp();
    }
//    //Phương thức truy cập vào thư viện hình ảnh của điện thoại hoặc máy ảo để lấy hình ra
//    private void openFileChoose() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
//    }
//
//    //Phương thức lấy hình
//    private String getFileExtension(Uri uri) {
//        ContentResolver contentResolver = getContentResolver();
//        MimeTypeMap typeMap = MimeTypeMap.getSingleton();
//        return typeMap.getExtensionFromMimeType(contentResolver.getType(uri));
//    }
//
//    //Phương thức úp hình lên firebase từ file image/...
//    private void uploadFile() {
//        if (imageUri != null)
//        {
//            uploadingFileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
//
//            uploadTask = uploadingFileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Uri> task) {
//                            Toast.makeText(Register_Activity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
//                            while (!task.isSuccessful()) {
//                                    imageUri = task.getResult();
//                                }
//                            if(task.isSuccessful())
//                            {
//                                String email = edtEmail.getText().toString().trim();
//                                String name = edtName.getText().toString().trim();
//                                String phone = edtPhone.getText().toString().trim();
//                                final KhachHang khachHang = new KhachHang(
//                                        ID,
//                                        Email,
//                                        Ten,
//                                        hoKhau,
//                                        Tuoi,
//                                        gioiTinh,
//                                        ngaySinh,
//                                        "https://" + task.getResult().getAuthority() + task.getResult().getEncodedPath() + "?" + task.getResult().getQuery(),
//                                        maXoa,
//                                        matKhau);
//                                String khachHangID = databaseReference.getKey();
//                                databaseReference.child(khachHangID).setValue(khachHang);
//                            }
//                            else
//                            {
//                                Toast.makeText(Register_Activity.this, "Error 404 !", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(Register_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//
//                }
//            });
//        }
//        else
//        {
//            Toast.makeText(this, "Vui lòng chọn hình ảnh", Toast.LENGTH_SHORT).show();
//        }
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
//                && data != null && data.getData() != null)
//        {
//            imageUri = data.getData();
//
//            Picasso.get().load(imageUri).into();
//        }
//    }
}