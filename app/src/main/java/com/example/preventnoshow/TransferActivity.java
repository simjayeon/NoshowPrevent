package com.example.preventnoshow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.preventnoshow.RemoteService.BASE_URL3;

public class TransferActivity extends AppCompatActivity {
    TextView btnTransfer, txtPlace, txtDate, txtTime, txtDiposit;
    FirebaseAuth mAuth;
    RemoteService remoteService;
    Retrofit retrofit;
    EditText edtName, edtTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL3)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        remoteService = retrofit.create(RemoteService.class);
        txtDate = findViewById(R.id.txtDate);
        txtPlace = findViewById(R.id.txtPlace);
        txtTime = findViewById(R.id.txtTime);
        txtDiposit = findViewById(R.id.txtDiposit);
        edtName = findViewById(R.id.edtName);
        edtTel = findViewById(R.id.edtTel);

        Intent intent = getIntent();
        txtDate.setText(intent.getStringExtra("txtDate"));
        txtPlace.setText(intent.getStringExtra("txtPlace"));
        txtTime.setText(intent.getStringExtra("txtTime"));
        txtDiposit.setText(intent.getStringExtra("txtDiposit"));

        btnTransfer = findViewById(R.id.btnTransfer);
        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getInstance().getCurrentUser();
                String email = user.getEmail();

                ResvVO vo = new ResvVO();

                vo.setEmail(email);
                vo.setRname(edtName.getText().toString());
                vo.setRtel(edtTel.getText().toString());
                vo.setRdate(intent.getStringExtra("txtDate"));
                vo.setRtime(intent.getStringExtra("txtTime"));
                vo.setBname(intent.getStringExtra("txtPlace"));

                if (edtName.getText().toString() == " " && edtTel.getText().toString() == " ") {
                    Toast.makeText(TransferActivity.this, "양도 받을 정보를 정확히 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Call<Void> call = remoteService.insertResv(vo.getEmail(), vo.getRname(), vo.getRtel(), vo.getRdate(), vo.getRtime(), vo.getBname());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(TransferActivity.this, "예약 양도 완료", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(TransferActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
}