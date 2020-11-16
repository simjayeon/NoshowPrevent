package com.example.preventnoshow;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class SearchFragment extends Fragment {
    EditText editSearch;
    TextView category, storeName, local;

    String url = "https://dapi.kakao.com/v2/local/search/address.json";
    String query = "미용실";
    int page =1;
    int total = 0;
    boolean isEnd=true;

    ArrayList<HashMap<String,String>> arrayPlace = new ArrayList<>();
    placeAdapter placeAdapter;
    RecyclerView listplace;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        editSearch = view.findViewById(R.id.editSearch);
        category = view.findViewById(R.id.category);
        storeName = view.findViewById(R.id.storeName);
        local = view.findViewById(R.id.local);

        placeAdapter = new placeAdapter();
        listplace = view.findViewById(R.id.listPlace);
        listplace.setLayoutManager(new LinearLayoutManager(getActivity()));
        listplace.setAdapter(placeAdapter);

        new placeThread().execute();
        return view;
    }

    class placeThread extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            String result = Kakao.connect(url + "?query="+query+"&page="+page);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            placeParsing(s);
            placeAdapter.notifyDataSetChanged();
            super.onPostExecute(s);
            System.out.println("지역"+arrayPlace.size());
        }
    }

    public void placeParsing(String result){
        try{
            JSONObject jsonObject = new JSONObject(result).getJSONObject("meta");
            total = jsonObject.getInt("total");
            isEnd = jsonObject.getBoolean("is_end");

            JSONArray jsonArray = new JSONObject(result).getJSONArray("documents");
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                HashMap <String,String> map = new HashMap<>();
                map.put("category", obj.getString("place_name"));
                map.put("storeName", obj.getString("place_name"));
                map.put("local", obj.getString("region_1depth_name"));
                System.out.println("..............."+obj.getString("place_name"));
                arrayPlace.add(map);
            }
        }catch (Exception e){

        }
    }

    class placeAdapter extends RecyclerView.Adapter<placeAdapter.ViewHolder>{

        @NonNull
        @Override
        public placeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.item_search, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull placeAdapter.ViewHolder holder, int position) {
            HashMap<String, String> map = arrayPlace.get(position);
            holder.category.setText(Html.fromHtml(map.get("category")));
            holder.storeName.setText(Html.fromHtml(map.get("storeName")));
            holder.local.setText(Html.fromHtml(map.get("local")));
            System.out.println("............................................");
        }

        @Override
        public int getItemCount() {
            return arrayPlace.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView category, storeName, local;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                category = itemView.findViewById(R.id.category);
                storeName = itemView.findViewById(R.id.storeName);
                local = itemView.findViewById(R.id.local);
            }
        }
    }
}
