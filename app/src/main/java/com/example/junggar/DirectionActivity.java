package com.example.junggar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.firestore.GeoPoint;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.ArrowheadPathOverlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.overlay.PathOverlay;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DirectionActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;

    double latitude;
    double longitude;

    ArrayList<LatLng> latLngArrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude",0.0);
        longitude = intent.getDoubleExtra("longitude",0.0);
        Log.d("Testing latitude", " " +latitude);

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map_fragment, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,  @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        String APIKEY_ID = "qjrwp3yzr9";
        String APIKEY = "Gq5rRONMUPWD8DWFNI3Qv2Oidn7kWqZmLU3XWUY5";

        PathOverlay drawpath = new PathOverlay();

        // 네이버 지도 ui setting
        UiSettings uiSettings = naverMap.getUiSettings();

        uiSettings.setLocationButtonEnabled(true);
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);



            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            NaverAPI naverapi = retrofit.create(NaverAPI.class);

            Call<ResultPath> call = naverapi.getPath(APIKEY_ID,APIKEY,"128.0984, 35.1545",longitude+", " +latitude,"trafast");

            call.enqueue(new Callback<ResultPath>() {
                @Override
                public void onResponse(Call<ResultPath> call, Response<ResultPath> response) {
                    if(response.isSuccessful()){
                        ResultPath resultPath = response.body();
                        Trafast t = resultPath.getRoute().getTrafast()[0];

                        Double[][] tmppath = t.getPath();


                        for(int i =0; i<tmppath.length; i++){

                            latLngArrayList.add(i,new LatLng(resultPath.getRoute().getTrafast()[0].getPath()[i][1],resultPath.getRoute().getTrafast()[0].getPath()[i][0]));

                            //Log.d("Testing Success", " " + resultPath.getRoute().getTrafast()[0].getPath()[i][0]);
                            //Log.d("Testing Success", " " + resultPath.getRoute().getTrafast()[0].getPath()[i][1]);
                        }


                        Log.d("Testing Success", ""+latLngArrayList.subList(0,latLngArrayList.size()));

                        drawpath.setCoords(latLngArrayList.subList(0,latLngArrayList.size()));
                        drawpath.setWidth(18);
                        drawpath.setOutlineWidth(2);
                        drawpath.setProgress(0);
                        drawpath.setPassedColor(Color.GRAY);
                        //drawpath.setPatternImage(OverlayImage.fromResource(R.drawable.arrow_path));
                        //drawpath.setPatternInterval(10);
                        drawpath.setMap(naverMap);


                        //Log.d("Testing Success", " " + t.toString());

                    }else{
                        Log.d("Testing failed", "onResponse : 실패");
                    }
                }

                @Override
                public void onFailure(Call<ResultPath> call, Throwable t) {

                }
            });

    }
}
