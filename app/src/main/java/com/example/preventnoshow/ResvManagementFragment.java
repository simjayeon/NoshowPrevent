package com.example.preventnoshow;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

import static com.example.preventnoshow.RemoteService.BASE_URL2;
import static com.example.preventnoshow.RemoteService.BASE_URL3;

public class ResvManagementFragment extends Fragment {
    Retrofit retrofit;
    RemoteService remoteService;
    List<ResvVO> resvList = new ArrayList<>();
    ResvAdapter resvAdapter = new ResvAdapter();
    ListView listResv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resv_management, container, false);

        listResv = view.findViewById(R.id.listResv);
        listResv.setAdapter(resvAdapter);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL3)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        remoteService = retrofit.create(RemoteService.class);

        Call<List<ResvVO>> call = remoteService.listResv();
        call.enqueue(new Callback<List<ResvVO>>() {
            @Override
            public void onResponse(Call<List<ResvVO>> call, Response<List<ResvVO>> response) {
                resvList = response.body();
                //System.out.println("..................."+storeList.size());
                resvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ResvVO>> call, Throwable t) {
                //System.out.println("..........."+t.toString());
            }
        });
        return view;
    }

        class ResvAdapter extends BaseAdapter {

            @Override
            public int getCount() {
                return resvList.size();
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
                view = getActivity().getLayoutInflater().inflate(R.layout.item_resv, parent, false);

                TextView txtPlace = view.findViewById(R.id.resvName2);
                TextView txtTime = view.findViewById(R.id.resvTime2);
                TextView txtDate = view.findViewById(R.id.resvDate2);
                TextView txtName = view.findViewById(R.id.name2);

                final ResvVO resvVO = resvList.get(position);
                txtPlace.setText(resvVO.getBname());
                txtTime.setText(resvVO.getRtime());
                txtDate.setText(resvVO.getRdate());
                txtName.setText(resvVO.getRname());

                /*
                ImageView open = view.findViewById(R.id.open);
                open.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Intent intent = new Intent(getActivity(), StoreDetailsActivity.class);
                        //intent.putExtra("storeTitle", storeVO.getBname()) ;
                        //intent.putExtra("address", storeVO.getAddress());
                        //intent.putExtra("txtIntro", storeVO.getIntro());
                        //startActivity(intent);
                    }
                });

                 */

                return view;
            }
    }
}
