package com.example.preventnoshow;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
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


public class SearchFragment extends Fragment {
    EditText editSearch;
    ImageView btnSearch;
    TextView category, storeName, local;
    Retrofit retrofit;
    RemoteService remoteService;
    List<StoreVO> storeList = new ArrayList<>();
    StoreAdapter storeAdapter = new StoreAdapter();
    ListView listStore;
    TextView txtTitle, txtLocal;
    String query = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        listStore = view.findViewById(R.id.listStore);
        listStore.setAdapter(storeAdapter);
        editSearch = view.findViewById(R.id.editSearch);
        btnSearch = view.findViewById(R.id.btnSearch);

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
                storeList = response.body();
                //System.out.println("..................."+storeList.size());
                storeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<StoreVO>> call, Throwable t) {
                //System.out.println("..........."+t.toString());
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeList.clear();
                query = editSearch.getText().toString();
                Call<StoreVO> call = remoteService.readStore(query);
                call.enqueue(new Callback<StoreVO>() {
                    @Override
                    public void onResponse(Call<StoreVO> call, Response<StoreVO> response) {
                        StoreVO storeVO = response.body();
                        storeAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<StoreVO> call, Throwable t) {

                    }
                });
                searchTest();
            }
        });
        return view;
    }

    public void searchTest(){

    }

    class StoreAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return storeList.size();
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
            view = getActivity().getLayoutInflater().inflate(R.layout.item_search, parent, false);
            final StoreVO storeVO = storeList.get(position);
            TextView txtCate = view.findViewById(R.id.category);
            txtTitle = view.findViewById(R.id.storeName);
            txtLocal = view.findViewById(R.id.local);
            String strLocal = storeVO.getAddress().substring(0,2);
            txtTitle.setText(storeVO.getSname()); //가게명
            txtLocal.setText(strLocal); //지역
            txtCate.setText("["+storeVO.getCategory()+"]"); //카테고리
            String strIntro = storeVO.getIntro();

            ImageView open = view.findViewById(R.id.open);
            open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), StoreDetailsActivity.class);
                    intent.putExtra("storeTitle", storeVO.getSname()) ;
                    intent.putExtra("address", storeVO.getAddress());
                    intent.putExtra("txtIntro", storeVO.getIntro());
                    intent.putExtra("category", storeVO.getCategory());
                    startActivity(intent);
                }
            });

            return view;
        }
    }

}