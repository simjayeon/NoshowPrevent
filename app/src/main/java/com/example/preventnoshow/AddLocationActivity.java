package com.example.preventnoshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AddLocationActivity extends AppCompatActivity {

    //private GoogleMap mMap;
    private Geocoder geocoder;
    EditText editSearch;
    ImageView btnSearch;

    String url = "https://dapi.kakao.com/v2/local/search/keyword.json";
    String query = "돼지";
    int page =1;
    int total = 0;
    boolean isEnd=true;

    ArrayList<HashMap<String,String>> arrayPlace = new ArrayList<>();
    PlaceAdapter placeAdapter = new PlaceAdapter();
    RecyclerView listPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        getSupportActionBar().setTitle("양도할 장소 검색");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        //검색 창
        editSearch = findViewById(R.id.editSearch);

        //검색버튼
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //arrayPlace.clear();
                query = editSearch.getText().toString();
                page = 1;
                new PlaceThread().execute();
            }
        });

        listPlace = findViewById(R.id.listPlace);
        listPlace.setLayoutManager(new LinearLayoutManager(this));
        listPlace.setAdapter(placeAdapter);
        new PlaceThread().execute();
    }


    class PlaceThread extends AsyncTask<String, String, String> {

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
            //txtTotal.setText("검색 수: "+ total + "/마지막 페이지 :" + isEnd);
            System.out.println("장소어떻게 나왔니"+arrayPlace.size());
        }
    }

    public void placeParsing(String result){
        try{
            arrayPlace = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(result).getJSONObject("meta");
            total = jsonObject.getInt("total_count");
            isEnd = jsonObject.getBoolean("is_end");

            JSONArray jsonArray = new JSONObject(result).getJSONArray("documents");
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                HashMap <String,String> map = new HashMap<>();
                map.put("title", obj.getString("place_name"));
                map.put("address", obj.getString("address_name"));
                map.put("tel", obj.getString("phone"));
                map.put("x", obj.getString("x"));
                map.put("y", obj.getString("y"));
                //System.out.println("..............."+obj.getString("place"));
                //System.out.println("..............."+obj.getString("y"));
                arrayPlace.add(map);
                //System.out.println(arrayPlace+"들어왔슴?");
            }
        }catch (Exception e){

        }
    }

    class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder>{
        @NonNull
        @Override
        public PlaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_place, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PlaceAdapter.ViewHolder holder, int position) {
            final HashMap<String, String> map = arrayPlace.get(position);
            holder.title.setText(Html.fromHtml(map.get("title")));
            holder.address.setText(Html.fromHtml(map.get("address")));
            if(map.get("tel").equals("")){
                holder.tel.setText("☎ 번호 없음");
            }else{
                holder.tel.setText(Html.fromHtml("☎ "+map.get("tel")));
            }
            holder.itemPlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        /*
                        RelativeLayout fragmentMaps = findViewById(R.id.fragmentMaps);
                        if(fragmentMaps.getVisibility()==view.VISIBLE){
                            fragmentMaps.setVisibility(View.GONE);
                        }else if(fragmentMaps.getVisibility()==view.GONE){
                            fragmentMaps.setVisibility(View.VISIBLE);
                        }
                         */

                    final Intent intent = new Intent(AddLocationActivity.this, BoardWriteActivity.class);
                    //intent.putExtra("x", map.get("x"));
                    //intent.putExtra("y", map.get("y"));
                    intent.putExtra("title", map.get("title"));
                    //intent.putExtra("address", map.get("address"));
                    //intent.putExtra("tel", map.get("tel"));
                    startActivity(intent);
                    finish();


                }
            });
        }

        @Override
        public int getItemCount() {
            return arrayPlace.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title, address, tel;
            RelativeLayout itemPlace;
            ImageView btnPlaceMaps;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.placeTitle);
                address = itemView.findViewById(R.id.placeAddress);
                tel = itemView.findViewById(R.id.placeTel);
                btnPlaceMaps = itemView.findViewById(R.id.placeMaps);
                itemPlace = itemView.findViewById(R.id.item_place);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}