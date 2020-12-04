package com.example.travel_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Login_Activity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    Button btnLogin;
    TextView tvNotHaveAccount, tvRecoverPass;

    private FirebaseAuth firebaseAuth;

    AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Trở lại");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        edtEmail = findViewById(R.id.edt_EmailForLogin);
        edtPassword = findViewById(R.id.edt_PassForLogin);
        btnLogin = findViewById(R.id.btn_ForLogin);
        tvNotHaveAccount = findViewById(R.id.tv_FromLoginToRegister);
        tvRecoverPass = findViewById(R.id.tv_RePassword);
        firebaseAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Truyền dữ liệu vào input id, email
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                //Bắt lỗi Email
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edtEmail.setError("Email không hợp lệ");
                    edtEmail.setFocusable(true);
                }
                //Bắt lỗi Password
                else if (password.length() < 6) {
                    edtPassword.setError("Password phải chứa ít nhất 6 kí tự");
                    edtPassword.setFocusable(true);
                } else {
                    loginKH(email, password); //lệnh đăng nhập user
                }
            }
        });

        tvNotHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Activity.this, Register_Activity.class));
                finish();
            }
        });

        //Quên mật khẩu
        tvRecoverPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPasswordDialog();
            }
        });
    }


    //Quên mật khẩu
    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Khôi phục mật khẩu");
        //set layout linear layout
        LinearLayout linearLayout = new LinearLayout(this);
        //views to set in dialog
        final EditText edtEmail = new EditText(this);
        edtEmail.setHint("Email");
        edtEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        edtEmail.setMinEms(10);

        linearLayout.addView(edtEmail);
        linearLayout.setPadding(10, 10, 10, 10);

        builder.setView(linearLayout);

        //create buttons recover
        builder.setPositiveButton("KHÔI PHỤC", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Input Email
                String email = edtEmail.getText().toString().trim();
                beginRecover(email);
            }
        });

        builder.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        //show dialog
        builder.create().show();
    }

    private void beginRecover(String email) {
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Đang gửi Email...");
        alertDialogBuilder.show();

    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Login_Activity.this, "Vui lòng kiểm tra hộp thư của bạn", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login_Activity.this, "Thất bại", Toast.LENGTH_SHORT).show();
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
                //get and show lỗi riêng
                Toast.makeText(Login_Activity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginKH(final String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if(email.equals("admin@gmail.com") && password.equals("admin123"))
                            {
                                if (task.getResult().getAdditionalUserInfo().isNewUser()) {
                                    String email = user.getEmail();
                                    String uid = user.getUid();
                                    //When user is registered store user info in firebase realtime database too
                                    //using Hashmap
                                    HashMap<Object, String> hashMap = new HashMap<>();
                                    //put info in hashmap
                                    hashMap.put("email", email);
                                    hashMap.put("uid", uid); //will add later (e.g.editprofile)
                                    hashMap.put("name", "");//will add later (e.g.editprofile)
                                    hashMap.put("phone", "");//will add later (e.g.editprofile)
                                    hashMap.put("image", "");//will add later (e.g.editprofile)
                                    hashMap.put("address", "");//will add later (e.g.editprofile)
                                    //firebase database instance
                                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                    //path to store user named "User"
                                    DatabaseReference databaseReference = firebaseDatabase.getReference("User");
                                    //put data within hashmap in database
                                    databaseReference.child(uid).setValue(hashMap);
                                }
                                Toast.makeText(Login_Activity.this, "Xin chào " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login_Activity.this, AdminActivity.class));
                                finish();
                            }
                            else
                            {
                                if (task.getResult().getAdditionalUserInfo().isNewUser()) {
                                    String email = user.getEmail();
                                    String uid = user.getUid();
                                    //When user is registered store user info in firebase realtime database too
                                    //using Hashmap
                                    HashMap<Object, String> hashMap = new HashMap<>();
                                    //put info in hashmap
                                    hashMap.put("email", email);
                                    hashMap.put("uid", uid); //will add later (e.g.editprofile)
                                    hashMap.put("name", "");//will add later (e.g.editprofile)
                                    hashMap.put("phone", "");//will add later (e.g.editprofile)
                                    hashMap.put("image", "");//will add later (e.g.editprofile)
                                    //firebase database instance
                                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                    //path to store user named "User"
                                    DatabaseReference databaseReference = firebaseDatabase.getReference("User");
                                    //put data within hashmap in database
                                    databaseReference.child(uid).setValue(hashMap);
                                }
                                Toast.makeText(Login_Activity.this, "Xin chào " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login_Activity.this, Navigation_Activity.class));
                                finish();
                            }

                        } else {
                            Toast.makeText(Login_Activity.this, " " + email, Toast.LENGTH_SHORT).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login_Activity.this, " " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();//câu lệnh trở về activity trước
        return super.onSupportNavigateUp();
    }
}
