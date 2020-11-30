package com.example.preventnoshow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.preventnoshow.RemoteService.BASE_URL4;

public class BoardWriteActivity extends AppCompatActivity {
    Retrofit retrofit;
    RemoteService remoteService;
    EditText edtTitle, edtContent, editDiposit;
    TextView selectDate, selectLocation, selectTime;
    LinearLayout btnAddLocation, btnAddDate, btnAddTime;
    String strId, strTitle, strContent, strPlace, strCt;
    FirebaseDatabase database ;
    DatabaseReference myRef;
    String[] categoryList = {"전체", "미용", "외식"};
    Spinner spinCategory;
    String a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        getSupportActionBar().setTitle("게시글 작성");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL4)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        remoteService = retrofit.create(RemoteService.class);

        edtTitle = (EditText) findViewById(R.id.editTitle);
        edtContent = (EditText) findViewById(R.id.editContent);
        selectDate = findViewById(R.id.selectDate);
        selectTime = findViewById(R.id.selectTime);
        selectLocation = findViewById(R.id.selectLocation);
        editDiposit = findViewById(R.id.editDiposit);
        selectLocation.setHint("위치 추가");

        btnAddTime = findViewById(R.id.btnAddTime);
        TimePickerDialog timeDialog = new TimePickerDialog(BoardWriteActivity.this,
                AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                selectTime.setText(hourOfDay + "시 " +minute + "분 ");
            }
        },11, 17, false);

        timeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnAddTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog.show();
            }
        });

        //spinner(카테고리)
        spinCategory = (Spinner) findViewById(R.id.spinCategory);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, categoryList);
        spinCategory.setAdapter(adapter);
        spinCategory.setSelection(0);


        //위치추가
        btnAddLocation = findViewById(R.id.btnAddLocation);
        btnAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BoardWriteActivity.this, AddLocationActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        selectLocation.setText(intent.getStringExtra("title"));

        //날짜추가
        btnAddDate = findViewById(R.id.btnAddDate);
        btnAddDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                new DatePickerDialog(BoardWriteActivity.this, mDateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)).show();
            }
        });
    }


    //데이터피커(달력) 띄우기 및 설정
    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            Calendar cal = Calendar.getInstance();
            selectDate.setText(cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE));
        }
    };

    DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                    selectDate.setText(String.format("%d-%d-%d", yy, mm + 1, dd));
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //취소버튼
            case android.R.id.home:
                AlertDialog.Builder box = new AlertDialog.Builder(BoardWriteActivity.this);
                box.setTitle("취소")
                        .setMessage("작성하시던 글을 취소하시겠습니까?\n작성된 글은 저장되지 않습니다.")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setNegativeButton("아니오", null)
                        .show();
                break;

            //등록버튼
            case R.id.save:

                strTitle=edtTitle.getText().toString().trim();
                strContent=edtContent.getText().toString().trim();


                if(strTitle.equals("")){
                    Toast.makeText(BoardWriteActivity.this, "제목을 입력하세요!", Toast.LENGTH_SHORT).show();
                }else if(strContent.equals("")){
                    Toast.makeText(BoardWriteActivity.this, "내용을 입력하세요!", Toast.LENGTH_SHORT).show();
                }else{
                    final AlertDialog.Builder box2 = new AlertDialog.Builder(BoardWriteActivity.this);
                            box2.setTitle("확인")
                            .setMessage("게시글을 등록하시겠습니까?\n*예약금은 기존 예약금보다 높을 시 처벌 대상이 될 수 있습니다.")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    long now = System.currentTimeMillis();
                                    Date date = new Date(now);
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                                    String formatDate = sdf.format(date);

                                    final Board board=new Board();
                                    board.setTitle(edtTitle.getText().toString());
                                    board.setPlace(selectLocation.getText().toString());
                                    board.setDate(selectDate.getText().toString());
                                    board.setTime(selectTime.getText().toString());
                                    board.setDiposit(editDiposit.getText().toString());
                                    board.setContents(edtContent.getText().toString());
                                    //board.setCreateDate(formatDate);

                                    Call<Void> call = remoteService.insertBoard(
                                            board.getTitle(), board.getPlace(), board.getDate(), board.getTime(), board.getDiposit(), board.getContents(), board.getCategory(), board.getCreateDate());
                                    call.enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            Toast.makeText(BoardWriteActivity.this, "게시글 작성 완료", Toast.LENGTH_SHORT).show();
                                            finish();
                                            Intent intent = new Intent(BoardWriteActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {

                                        }
                                    });
                                }
                            }).setNegativeButton("아니오", null)
                            .show();

                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

