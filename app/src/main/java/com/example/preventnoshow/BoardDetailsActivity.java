package com.example.preventnoshow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class BoardDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    TextView txtTitle, txtDate, txtTime, txtPlace, txtDiposit, txtContent;
    TextView btnResvRecv;
    private GoogleMap mMap;
    private Geocoder geocoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_details);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        txtTitle = findViewById(R.id.txtTitle);
        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);
        txtPlace = findViewById(R.id.txtPlace);
        txtDiposit = findViewById(R.id.txtDiposit);
        txtContent = findViewById(R.id.txtContent);
        btnResvRecv = findViewById(R.id.btnResvRecv);

        Intent intent = getIntent();
        txtTitle.setText(intent.getStringExtra("txtTitle"));
        txtDate.setText(intent.getStringExtra("txtDate"));
        txtTime.setText(intent.getStringExtra("txtTime"));
        txtPlace.setText(intent.getStringExtra("txtPlace"));
        txtDiposit.setText(intent.getStringExtra("txtDiposit"));
        txtContent.setText(intent.getStringExtra("txtContent"));

        //버튼 누르면 채팅으로
        btnResvRecv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(BoardDetailsActivity.this, TransferActivity.class);
                intent1.putExtra("txtDate", txtDate.getText().toString());
                intent1.putExtra("txtTime", txtTime.getText().toString());
                intent1.putExtra("txtDiposit", txtDiposit.getText().toString());
                intent1.putExtra("txtPlace", txtPlace.getText().toString());
                startActivity(intent1);
                finish();
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this);

        String str = txtPlace.getText().toString();
        List<Address> addressList = null;
        try{
            addressList = geocoder.getFromLocationName(str, 10);
        }catch (IOException e){
            e.printStackTrace();
        }

        String []splitStr = addressList.get(0).toString().split(",");
        String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length() - 2); // 주소
       // System.out.println(address);

        String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
        String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도

        // 좌표(위도, 경도) 생성
        LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

        MarkerOptions mOptions = new MarkerOptions();
        mOptions.title(str);
        mOptions.snippet(address);
        mOptions.position(point);
        mMap.addMarker(mOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));

    }
}