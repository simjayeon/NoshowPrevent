package com.example.preventnoshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.preventnoshow.RemoteService.BASE_URL3;


public class ReservActivity extends AppCompatActivity {
    CalendarView calendarView;
    LinearLayout resvTimeLayout;
    TextView txtDate, txtTime, txtTitie, btnResvSuccess, editName, editTel;
    RemoteService remoteService;
    Retrofit retrofit;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserv);
        //getActionBar().setTitle("예약 하기");
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        calendarView = findViewById(R.id.calendarView);
        txtTitie = findViewById(R.id.txtTitle);
        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);
        resvTimeLayout = findViewById(R.id.resvTimeLayout);
        btnResvSuccess = findViewById(R.id.btnResvSuccess);
        editName = findViewById(R.id.editName);
        Intent intent = getIntent();
        txtTitie.setText(intent.getStringExtra("txtTitie"));
        editTel = findViewById(R.id.editTel);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL3)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        remoteService = retrofit.create(RemoteService.class);


        //캘린더로 날짜 선택
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                txtDate.setText(year+"-"+(month+1)+"-"+dayOfMonth);
            }
        });


        //시간 선택
        TimePickerDialog timeDialog = new TimePickerDialog(ReservActivity.this,
                AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                txtTime.setText(hourOfDay + "시 " +minute + "분");
            }
        },11, 17, false);

        timeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        resvTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog.show();
            }
        });

        //FirebaseUser user = mAuth.getCurrentUser();
        //String str = user.getDisplayName().toString();
        //System.out.println(str+".,................");
        btnResvSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder box = new AlertDialog.Builder(ReservActivity.this);
                box.setTitle("확인")
                        .setMessage("예약을 완료하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseUser user = mAuth.getInstance().getCurrentUser();
                                String email = user.getEmail();

                                ResvVO vo = new ResvVO();

                                vo.setEmail(email);
                                vo.setRname(editName.getText().toString());
                                vo.setRtel(editTel.getText().toString());
                                vo.setRdate(txtDate.getText().toString());
                                vo.setRtime(txtTime.getText().toString());
                                vo.setBname(txtTitie.getText().toString());

                                if(vo.getRdate().equals("2020-00-00")||vo.getRtime().equals("시간 선택")|vo.getRname().equals(" ")|vo.getRtel().equals(" ")){
                                    Toast.makeText(ReservActivity.this, "내용을 정확히 입력해주세요.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Call<Void> call = remoteService.insertResv(vo.getEmail(),vo.getRname(), vo.getRtel(), vo.getRdate(), vo.getRtime(), vo.getBname());
                                    call.enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            Toast.makeText(ReservActivity.this, "예약 완료", Toast.LENGTH_SHORT).show();
                                            finish();
                                            Intent intent = new Intent(ReservActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {

                                        }
                                    });
                                }
                            }
                        })
                        .setNegativeButton("아니오", null)
                        .show();
            }
        });

    }
}