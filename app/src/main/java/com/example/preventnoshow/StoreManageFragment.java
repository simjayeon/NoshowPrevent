package com.example.preventnoshow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class StoreManageFragment extends Fragment {
    FloatingActionButton btnAdd;
    Retrofit retrofit;
    RemoteService remoteService;
    ListView listSM;
    SMAdapter smAdapter = new SMAdapter();
    List<StoreVO> smList = new ArrayList<>();
    TextView btnDelete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_store_manage, container, false);

       btnAdd = view.findViewById(R.id.btnAdd);
       btnAdd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getActivity(), StoreRegisterActivity.class);
               startActivity(intent);
           }
       });

       listSM = view.findViewById(R.id.blistStore);
       listSM.setAdapter(smAdapter);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL2)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        remoteService = retrofit.create(RemoteService.class);

        Call<List<StoreVO>> call = remoteService.listStore();
        call.enqueue(new Callback<List<StoreVO>>() {
            @Override
            public void onResponse(Call<List<StoreVO>> call, Response<List<StoreVO>> response) {
                smList = response.body();
                smAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<StoreVO>> call, Throwable t) {

            }
        });
        return view;
    }

    class SMAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return smList.size();
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
            view = getActivity().getLayoutInflater().inflate(R.layout.item_boss, parent, false);
            TextView sName = view.findViewById(R.id.sName);
            TextView bName = view.findViewById(R.id.bName);
            TextView sId = view.findViewById(R.id.sId);
            TextView tel = view.findViewById(R.id.tel);

            final StoreVO storeVO = smList.get(position);
            sName.setText(storeVO.getSname());
            bName.setText(storeVO.getBname());
            sId.setText(storeVO.getSid());
            tel.setText(storeVO.getTel());

            btnDelete = view.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder box = new AlertDialog.Builder(getActivity());
                    box.setMessage("삭제하시겠습니까?");
                    box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String strSid = sId.getText().toString();
                            Call<Void> call = remoteService.deleteStore(strSid);
                            call.enqueue(new Callback<Void>() {
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Toast.makeText(getActivity(), "가게정보 삭제", Toast.LENGTH_SHORT);
                                    onResume();
                                }
                                public void onFailure(Call<Void> call, Throwable t) { }
                            });
                        }
                    });
                    box.setNegativeButton("아니오", null);
                    box.show();
                }
            });


            return view;
        }
    }
}