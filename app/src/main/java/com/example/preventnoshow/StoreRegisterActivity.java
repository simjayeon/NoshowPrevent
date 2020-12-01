package com.example.preventnoshow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.preventnoshow.RemoteService.BASE_URL2;

public class StoreRegisterActivity extends AppCompatActivity {
    EditText edtbName, edtsName, edtsId, edtTel, edtAddress, edtIntro;
    RadioButton rFood, rBeauty;
    Retrofit retrofit;
    RemoteService remoteService;
    TextView btnRegister;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_register);

        edtbName = findViewById(R.id.edtbName);
        edtsName = findViewById(R.id.edtsName);
        edtsId = findViewById(R.id.edtsId);
        edtTel = findViewById(R.id.edtTel);
        edtAddress = findViewById(R.id.edtAddress);
        edtIntro = findViewById(R.id.edtIntro);
        rFood = findViewById(R.id.rFood);
        rBeauty = findViewById(R.id.rBeauty);
        btnRegister = findViewById(R.id.btnRegister);

        FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        String email = currentUser.getEmail();
        System.out.println(email+"@@@@@");

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL2)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        remoteService = retrofit.create(RemoteService.class);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rFood.isChecked()){
                    final StoreVO storeVO = new StoreVO();
                    storeVO.setEmail(email);
                    storeVO.setBname(edtbName.getText().toString());
                    storeVO.setSname(edtsName.getText().toString());
                    storeVO.setSid(edtsId.getText().toString());
                    storeVO.setTel(edtTel.getText().toString());
                    storeVO.setAddress(edtAddress.getText().toString());
                    storeVO.setIntro(edtIntro.getText().toString());
                    storeVO.setCategory(rFood.getText().toString());

                    Call<Void> call = remoteService.insertStore(storeVO.getEmail(), storeVO.getSid(), storeVO.getSname(),
                            storeVO.getBname(), storeVO.getAddress(), storeVO.getTel(), storeVO.getIntro(), storeVO.getCategory());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(StoreRegisterActivity.this, "가게등록 완료", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(StoreRegisterActivity.this, BossMainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                }else if(rBeauty.isChecked()){
                    final StoreVO storeVO = new StoreVO();
                    storeVO.setEmail(email);
                    storeVO.setBname(edtbName.getText().toString());
                    storeVO.setSname(edtsName.getText().toString());
                    storeVO.setSid(edtsId.getText().toString());
                    storeVO.setTel(edtTel.getText().toString());
                    storeVO.setAddress(edtAddress.getText().toString());
                    storeVO.setIntro(edtIntro.getText().toString());
                    storeVO.setCategory(rBeauty.getText().toString());

                    Call<Void> call = remoteService.insertStore(storeVO.getEmail(), storeVO.getSid(), storeVO.getSname(),
                            storeVO.getBname(), storeVO.getAddress(), storeVO.getTel(), storeVO.getIntro(), storeVO.getCategory());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(StoreRegisterActivity.this, "가게등록 완료", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(StoreRegisterActivity.this, BossMainActivity.class);
                            startActivity(intent);
                            finish();
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