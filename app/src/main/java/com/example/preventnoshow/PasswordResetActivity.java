package com.example.preventnoshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class PasswordResetActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextView btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        mAuth = FirebaseAuth.getInstance();

        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();
            }
        });
    }

    //이메일보내기
    private void send(){
        String email = ((EditText) findViewById(R.id.edtEmail)).getText().toString();
        if(email.length()>0){
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            //DatabaseReference reference = database.getReference("simstagram").child("Users").child("UserInfo").child(uid);
                            if (task.isSuccessful()) {
                                Toast.makeText(PasswordResetActivity.this,
                                        "입력한 이메일로 발송했습니다.",Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(PasswordResetActivity.this,
                                        "이메일을 정확히 입력해주세요.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Toast.makeText(PasswordResetActivity.this, "비밀번호 재설정 취소", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PasswordResetActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}