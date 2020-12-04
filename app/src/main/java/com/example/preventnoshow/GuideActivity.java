package com.example.preventnoshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GuideActivity extends AppCompatActivity {
    ImageView open01, open02, open03, open04, open05;
    TextView guideContext01, guideContext02, guideContext03, guideContext04, guideContext05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        open01 = findViewById(R.id.open01);
        open02 = findViewById(R.id.open02);
        open03 = findViewById(R.id.open03);
        open04 = findViewById(R.id.open04);
        open05 = findViewById(R.id.open05);

        guideContext01 = findViewById(R.id.guideContext01);
        guideContext02 = findViewById(R.id.guideContext02);
        guideContext03 = findViewById(R.id.guideContext03);
        guideContext04 = findViewById(R.id.guideContext04);
        guideContext05 = findViewById(R.id.guideContext05);

        open01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(guideContext01.getVisibility() == View.GONE){
                    guideContext01.setVisibility(View.VISIBLE);
                    open01.setImageResource(R.drawable.ic_up);
                } else if(guideContext01.getVisibility() == View.VISIBLE){
                    guideContext01.setVisibility(View.GONE);
                    open01.setImageResource(R.drawable.ic_down);
                }
            }
        });

        open02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(guideContext02.getVisibility() == View.GONE){
                    guideContext02.setVisibility(View.VISIBLE);
                    open02.setImageResource(R.drawable.ic_up);
                } else if(guideContext02.getVisibility() == View.VISIBLE){
                    guideContext02.setVisibility(View.GONE);
                    open02.setImageResource(R.drawable.ic_down);
                }
            }
        });

        open03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(guideContext03.getVisibility() == View.GONE){
                    guideContext03.setVisibility(View.VISIBLE);
                    open03.setImageResource(R.drawable.ic_up);
                } else if(guideContext03.getVisibility() == View.VISIBLE){
                    guideContext03.setVisibility(View.GONE);
                    open03.setImageResource(R.drawable.ic_down);
                }
            }
        });

        open04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(guideContext04.getVisibility() == View.GONE){
                    guideContext04.setVisibility(View.VISIBLE);
                    open04.setImageResource(R.drawable.ic_up);
                } else if(guideContext04.getVisibility() == View.VISIBLE){
                    guideContext04.setVisibility(View.GONE);
                    open04.setImageResource(R.drawable.ic_down);
                }
            }
        });

        open05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(guideContext05.getVisibility() == View.GONE){
                    guideContext05.setVisibility(View.VISIBLE);
                    open05.setImageResource(R.drawable.ic_up);
                } else if(guideContext05.getVisibility() == View.VISIBLE){
                    guideContext05.setVisibility(View.GONE);
                    open05.setImageResource(R.drawable.ic_down);
                }
            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}