package com.example.travel_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ChangePassword_Activity extends AppCompatActivity {
    EditText edtCurrentPassword, edtNewPassword, edtConfirmPassword;
    Button btnChangePassword;

    AlertDialog.Builder alertDialogBuilder;

    FirebaseAuth firebaseAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_changepassword);

        edtCurrentPassword = findViewById(R.id.edt_currentPassword);
        edtNewPassword = findViewById(R.id.edt_newPassword);
        edtConfirmPassword = findViewById(R.id.edt_confirmPassword);
        btnChangePassword = findViewById(R.id.btn_ChangePassword);
        firebaseAuth = firebaseAuth.getInstance();

        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword_Activity.this);
        AlertDialog dialog = builder.create();
        dialog.show();

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentPassword = edtCurrentPassword.getText().toString().trim();
                String newPassword = edtNewPassword.getText().toString().trim();
                String confirmPassword = edtConfirmPassword.getText().toString().trim();
                if (TextUtils.isEmpty(currentPassword)) {
                    Toast.makeText(ChangePassword_Activity.this, "Nhập vào mật khẩu hiện tại", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (newPassword.length() < 6) {
                    Toast.makeText(ChangePassword_Activity.this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
                }
                if (confirmPassword.length() < 6) {
                    Toast.makeText(ChangePassword_Activity.this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
                }
                else if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(ChangePassword_Activity.this, "Mật khẩu không trùng khớp với mật khẩu mới!", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
                updatePassword(currentPassword, newPassword, confirmPassword);
            }
        });
    }

    private void updatePassword(String currentPassword, String newPassword, String confirmPassword) {
        alertDialogBuilder = new AlertDialog.Builder(this);

        //get user hiện tại
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //before changing password re-authenticate the user
        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);
        user.reauthenticate(authCredential)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        user.updatePassword(newPassword)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        alertDialogBuilder.show();
                                        Toast.makeText(ChangePassword_Activity.this, "Mật khẩu đã được thay đổi!", Toast.LENGTH_SHORT).show();
                                        System.exit(0);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        alertDialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                            @Override
                                            public void onCancel(DialogInterface dialog) {
                                                dialog.dismiss();
                                                Toast.makeText(ChangePassword_Activity.this, "", Toast.LENGTH_SHORT).show();
                                            }
                                        });
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
                                Toast.makeText(ChangePassword_Activity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }
}

