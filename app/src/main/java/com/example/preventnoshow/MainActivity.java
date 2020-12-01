package com.example.preventnoshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private View drawerView;
    ViewPager pager;
    TabLayout tab;
    ArrayList<Fragment> array;

    LinearLayout lineartop, linearbottom, linearprofile, linearmember;
    ImageView profileimage;
    TextView inquire, aboutus, contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //액션바 설정
        getSupportActionBar().setTitle("노쇼");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerView = (View) findViewById(R.id.drawerView);

        lineartop = findViewById(R.id.lineartop);
        linearbottom = findViewById(R.id.linearbottom);
        linearprofile = findViewById(R.id.linearprofile);
        linearmember = findViewById(R.id.linearmember);

        profileimage = findViewById(R.id.profileimage);

        aboutus = findViewById(R.id.aboutus);
        inquire = findViewById(R.id.inquire);
        contact = findViewById(R.id.contact);
        pager = findViewById(R.id.pager);
        tab = findViewById(R.id.tab);

        //탭에 텍스트 추가
        tab.addTab(tab.newTab()); //홈
        tab.addTab(tab.newTab()); //검색
        tab.addTab(tab.newTab()); //게시판
        tab.addTab(tab.newTab()); //마이페이지

        //탭에 아이콘 추가
        tab.getTabAt(0).setIcon(R.drawable.home);
        tab.getTabAt(1).setIcon(R.drawable.store);
        tab.getTabAt(2).setIcon(R.drawable.free_icon);
        tab.getTabAt(3).setIcon(R.drawable.user2);

        //탭을 누를 때때마다 포지션이바뀌는 작업
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AboutusActivity.class));
            }
        });

        inquire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InquireActivity.class));
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ContactActivity.class));
            }
        });

        //뷰페이저에 들어갈 프래그먼트를 프래그먼크 배열에 저장
        array = new ArrayList<>();
        array.add(new HomeFragment());
        array.add(new SearchFragment());
        array.add(new BoardFragment());
        array.add(new MyPageFragment());

        //페이저 바뀌는 작업
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));

        //페이저어댑터를 통해 프래그먼트 설정
        PagerAdapter ad = new PagerAdapter(getSupportFragmentManager());
        pager.setAdapter(ad);
    }

    //페이저어댑터 생성_프래그먼트어댑터
    class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return array.get(position);
        }

        @Override
        public int getCount() {
            return array.size();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.chat :
                Intent intent = new Intent(MainActivity.this, ChatListActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}