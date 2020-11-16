package com.example.preventnoshow;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class TransactionFragment extends Fragment {
    RecyclerView listBoard;
    BoardAdapter boardAdapter;
    ArrayList<Board> arrayBoard = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_transaction, container, false);

        //tranAdapter = new tranAdapter(this, cur);
        listBoard = view.findViewById(R.id.listBoard);
        listBoard.setLayoutManager(new LinearLayoutManager(getActivity()));
        listBoard.setAdapter(boardAdapter);


        return view;
    }


    class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder>{

        @NonNull
        @Override
        public BoardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.item_search, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BoardAdapter.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return arrayBoard.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}

