package com.example.preventnoshow;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class StoreManageFragment extends Fragment {
    FloatingActionButton btnAdd;

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


        return view;
    }
}