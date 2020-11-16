package com.example.preventnoshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    EditText editEmail, editPassword;
    TextView btnLogin;
    LinearLayout btnSignUp, btnPwReset;
    String strEmail, strPassword;
    FirebaseAuth mAuth;
    RadioButton rbtnCustomer, rbtnBoss;
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        rbtnCustomer = findViewById(R.id.checkCustomer);
        rbtnBoss = findViewById(R.id.checkBoss);


        //회원가입 버튼
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] userChoice = new String[]{"Customer", "Boss"};
                AlertDialog.Builder dig = new AlertDialog.Builder(LoginActivity.this);
                dig.setTitle("사용자 선택");
                dig.setItems(userChoice, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str = userChoice[which].toString();
                        if(str == "Customer"){
                            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                            startActivity(intent);
                            finish();
                        }else if(str == "Boss"){
                            Intent intent = new Intent(LoginActivity.this, Register2Activity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }).show();

            }
        });

        //로그인
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail = editEmail.getText().toString();
                strPassword = editPassword.getText().toString();
                if(rbtnCustomer.isChecked()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else if(rbtnBoss.isChecked()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent intent = new Intent(LoginActivity.this, BossMainActivity.class);
                    startActivity(intent);
                    finish();
                }
                loginUser(strEmail, strPassword);
            }
        });

        btnPwReset = findViewById(R.id.btnPwReset);
        btnPwReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, PasswordResetActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void loginUser(String Email, String Password){
        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent (LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this, "잘못된 이메일 및 비밀번호입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}