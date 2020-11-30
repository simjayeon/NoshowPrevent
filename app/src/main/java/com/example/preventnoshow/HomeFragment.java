package com.example.preventnoshow;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.preventnoshow.RemoteService.BASE_URL3;


public class HomeFragment extends Fragment {
    Retrofit retrofit;
    RemoteService remoteService;
    ListView lisHome;
    HomeAdapter homeAdapter = new HomeAdapter();
    List<ResvVO> homeList = new ArrayList<>();
    ImageView down;
    CalendarView calendarView;
    String strDate;
    int a = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        down = view.findViewById(R.id.down);
        calendarView = view.findViewById(R.id.calendarView);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a == 0){
                    calendarView.setVisibility(View.GONE);
                    down.setImageResource(R.drawable.ic_up);
                    a = 1;
                }else if(a == 1){
                    calendarView.setVisibility(View.VISIBLE);
                    down.setImageResource(R.drawable.ic_down);
                    a = 0;
                }
            }
        });

        lisHome = view.findViewById(R.id.listhome);
        lisHome.setAdapter(homeAdapter);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL3)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        remoteService = retrofit.create(RemoteService.class);

        Call<List<ResvVO>> call2 = remoteService.listResv();
        call2.enqueue(new Callback<List<ResvVO>>() {
            @Override
            public void onResponse(Call<List<ResvVO>> call, Response<List<ResvVO>> response) {
                homeList = response.body();
                homeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ResvVO>> call, Throwable t) {
                System.out.println(",,,,,,,,,,,"+t.toString());
            }
        });

        return view;
    }


    class HomeAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return homeList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            view = getActivity().getLayoutInflater().inflate(R.layout.item_home, parent, false);

            TextView txtPlace = view.findViewById(R.id.resvName2);
            TextView txtTime = view.findViewById(R.id.resvTime2);
            TextView txtDate = view.findViewById(R.id.resvDate2);
            TextView txtName = view.findViewById(R.id.name2);

            final ResvVO resvVO = homeList.get(position);
            txtPlace.setText(resvVO.getBname());
            txtTime.setText(resvVO.getRtime());
            txtDate.setText(resvVO.getRdate());
            txtName.setText(resvVO.getRname());

            return view;
        }
    }
}