package com.example.preventnoshow;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import retrofit2.Retrofit;

public class Register2Activity extends AppCompatActivity {
    EditText edtEmail, edtPassword, edtName, edtPwCheck;
    TextView btnRegister, btnCancel;
    String strEmail, strPassword, strName, strPwCheck, strSex, strFemale, strMale, infoAgree, position;
    RadioGroup sex;
    RadioButton female, male;
    CheckBox checkBox1, checkBox2;
    ImageView checkimg;

    private FirebaseAuth mAuth;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        //firebase
        mAuth = FirebaseAuth.getInstance();

        //editText
        edtEmail=findViewById(R.id.edtEmail);
        edtPassword=findViewById(R.id.edtPassword);
        edtName = findViewById(R.id.edtName);
        edtPwCheck = findViewById(R.id.edtPwcheck);

        //radioGruop, button
        sex = findViewById(R.id.sex);
        female = findViewById(R.id.female);
        male = findViewById(R.id.male);

        //checkbox
        checkBox1 = findViewById(R.id.chbox1); //필수
        checkBox2 = findViewById(R.id.chbox2); //선택

        infoAgree = "동의안함";

        checkimg = findViewById(R.id.checkimg);

        edtPwCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(edtPassword.getText().toString().equals(edtPwCheck.getText().toString())){
                    checkimg.setImageResource(R.drawable.ic_check);
                }else{
                    checkimg.setImageResource(R.drawable.ic_x);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //회원가입 button
        btnRegister=findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                strEmail = edtEmail.getText().toString();
                strPassword = edtPassword.getText().toString();
                strName = edtName.getText().toString();
                strPwCheck = edtPwCheck.getText().toString();
                strFemale = female.getText().toString();
                strMale = male.getText().toString();


                final ProgressDialog mDialog = new ProgressDialog(Register2Activity.this);
                if(checkBox1.isChecked()){
                    if (strPassword.equals(strPwCheck)) {
                        mDialog.setMessage("가입중입니다...");
                        mDialog.show();

                        mAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                                .addOnCompleteListener(Register2Activity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {


                                        //가입 성공시
                                        if (task.isSuccessful()) {
                                            mDialog.dismiss();

                                            FirebaseUser user = mAuth.getCurrentUser();
                                            String email = user.getEmail();
                                            String uid = user.getUid();
                                            String name = edtName.getText().toString().trim();
                                            String pw = edtPassword.getText().toString().trim();

                                            if(female.isChecked()){
                                                strSex = strFemale;
                                            }else if(male.isChecked()){
                                                strSex = strMale;
                                            }

                                            if(checkBox2.isChecked()){
                                                Toast.makeText(Register2Activity.this, "메시지 수신 및 알림 : 동의", Toast.LENGTH_SHORT).show();
                                                infoAgree = "개인정보 동의";
                                            }else{
                                                Toast.makeText(Register2Activity.this, "메시지 수신 및 알림 : 비 동의", Toast.LENGTH_SHORT).show();
                                            }

                                            position = "boss";

                                            //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                                            HashMap<Object,String> hashMap = new HashMap<>();

                                            hashMap.put("uid",uid);
                                            hashMap.put("email",email);
                                            hashMap.put("name",name);
                                            hashMap.put("password", pw);
                                            hashMap.put("sex", strSex);
                                            hashMap.put("infoAgree", infoAgree);
                                            hashMap.put("position", position);

                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            DatabaseReference reference = database.getReference("Noshow").child("Users").child("Customer").child("Info").child(uid);
                                            reference.setValue(hashMap);

                                            //가입이 이루어져을시 가입 화면을 빠져나감
                                            Toast.makeText(Register2Activity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Register2Activity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();

                                        } else {
                                            mDialog.dismiss();
                                            Toast.makeText(Register2Activity.this, "이미 존재하는 이메일일 입니다.", Toast.LENGTH_SHORT).show();
                                            return;  //해당 메소드 진행을 멈추고 빠져나감

                                        }

                                    }
                                });

                        //비밀번호 오류시
                    }else{
                        Toast.makeText(Register2Activity.this, "비밀번호가 일치하지 않습니다.\n 다시 입력해 주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //필수항목 체크되어있지 않을 때
                }else{
                    AlertDialog.Builder box = new AlertDialog.Builder(Register2Activity.this);
                    box.setMessage("개인정보 수집에 대한 동의가 필요합니다.")
                            .setPositiveButton("확인", null)
                            .show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Toast.makeText(Register2Activity.this, "회원가입 취소", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register2Activity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}