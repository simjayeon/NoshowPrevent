package com.example.preventnoshow;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.preventnoshow.RemoteService.BASE_URL;
import static com.example.preventnoshow.RemoteService.BASE_URL3;
import static com.example.preventnoshow.RemoteService.BASE_URL4;


public class BoardFragment extends Fragment {
    TextView addWrite;
    Retrofit retrofit;
    RemoteService remoteService;
    List<Board> boardList = new ArrayList<>();
    ListView listBoard;
    BoardAdapter boardAdapter = new BoardAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board, container, false);
        addWrite = view.findViewById(R.id.addWrite);
        addWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BoardWriteActivity.class);
                startActivity(intent);
            }
        });

        listBoard = view.findViewById(R.id.listBoard);
        listBoard.setAdapter(boardAdapter);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL4)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        remoteService = retrofit.create(RemoteService.class);

        Call<List<Board>> call = remoteService.listBoard();
        call.enqueue(new Callback<List<Board>>() {
            @Override
            public void onResponse(Call<List<Board>> call, Response<List<Board>> response) {
                boardList = response.body();
                boardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Board>> call, Throwable t) {
                System.out.println(",,,,,,,,,,,"+t.toString());
            }
        });

        return view;
    }

    class BoardAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return boardList.size();
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
            view = getActivity().getLayoutInflater().inflate(R.layout.item_board, parent, false);
            TextView txtTitle = view.findViewById(R.id.title);
            TextView txtContents = view.findViewById(R.id.txtContents);
            TextView txtCategory = view.findViewById(R.id.category);

            final  Board board = boardList.get(position);
            txtTitle.setText(board.getTitle());
            String str = board.getContents();
            System.out.println(str);
            txtContents.setText(str);
            //txtCreateDate.setText(board.getCreateDate());
            String strDate = board.getDate();
            String strTime = board.getTime();

            ImageView open = view.findViewById(R.id.open);
            open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), BoardDetailsActivity.class);
                    intent.putExtra("txtTitle", board.getTitle()) ;
                    intent.putExtra("txtDate", strDate) ;
                    intent.putExtra("txtTime", board.getTime()) ;
                    intent.putExtra("txtDiposit", board.getDiposit()) ;
                    intent.putExtra("txtPlace", board.getPlace()) ;
                    intent.putExtra("txtContent", board.getContents());
                    startActivity(intent);
                }
            });

            return view;
        }
    }
}