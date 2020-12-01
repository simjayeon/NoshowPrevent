package com.example.preventnoshow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class StoreDetailsActivity extends AppCompatActivity {
    TextView btnResv, storeTitle, txtIntro, address;
    ImageView imgMap, thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details);

        storeTitle = findViewById(R.id.storeTitle);
        txtIntro = findViewById(R.id.txtIntro);
        address = findViewById(R.id.address);
        thumbnail = findViewById(R.id.titleImage);

        Intent intent = getIntent();
        storeTitle.setText(intent.getStringExtra("storeTitle"));
        txtIntro.setText(intent.getStringExtra("txtIntro"));
        address.setText(intent.getStringExtra("address"));
        String strCategory = intent.getStringExtra("category");

        //이미지
        if(strCategory=="외식"){
            thumbnail.setImageResource(R.drawable.asdf); //수정해야함
        }else if(strCategory=="미용"){
            thumbnail.setImageResource(R.drawable.asdf);
        }

        imgMap = findViewById(R.id.imgMap);
        imgMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(StoreDetailsActivity.this, MapsActivity.class);
                startActivity(intent1);
            }
        });


        btnResv = findViewById(R.id.btnResv);
        btnResv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreDetailsActivity.this, ReservActivity.class);
                intent.putExtra("txtTitie", storeTitle.getText());
                startActivity(intent);
                finish();
            }
        });
    }
}