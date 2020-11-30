package com.example.preventnoshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class BossMainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ViewPager pager;
    TabLayout tab;
    ArrayList<Fragment> array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_main);

        drawerLayout = findViewById(R.id.drawerLayout); //서랍
        pager = findViewById(R.id.pager);
        tab = findViewById(R.id.tab);

        tab.addTab(tab.newTab()); //홈
        tab.addTab(tab.newTab()); //예약현황
        tab.addTab(tab.newTab()); //마이페이지

        //탭에 아이콘 추가
        tab.getTabAt(0).setIcon(R.drawable.home);
        tab.getTabAt(1).setIcon(R.drawable.logo);
        tab.getTabAt(2).setIcon(R.drawable.user2);


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

        //프래그먼트 추가
        array = new ArrayList<>();
        array.add(new BhomeFragment());
        array.add(new ResvManagementFragment());

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
}