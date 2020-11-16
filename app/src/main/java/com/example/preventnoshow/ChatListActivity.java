package com.example.preventnoshow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ChatListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        getSupportActionBar().setTitle("채팅목록");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}