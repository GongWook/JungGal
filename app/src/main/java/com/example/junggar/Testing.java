package com.example.junggar;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Testing  extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        String APIKEY_ID = "qjrwp3yzr9";
        String APIKEY = "Gq5rRONMUPWD8DWFNI3Qv2Oidn7kWqZmLU3XWUY5";


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NaverAPI naverapi = retrofit.create(NaverAPI.class);

        Call<ResultPath> call = naverapi.getPath(APIKEY_ID,APIKEY,"129.089441, 35.231100","129.084454, 35.228982","trafast");

        call.enqueue(new Callback<ResultPath>() {
            @Override
            public void onResponse(Call<ResultPath> call, Response<ResultPath> response) {
                if(response.isSuccessful()){
                    ResultPath resultPath = response.body();
                    Trafast t = resultPath.getRoute().getTrafast()[0];

                    Double[][] tmppath = t.getPath();

                    for(int i =0; i<tmppath.length; i++){
                        resultPath.getRoute().getTrafast()[0].getPath();
                        Log.d("Testing Success", " " + resultPath.getRoute().getTrafast()[0].getPath()[i][0]);
                        Log.d("Testing Success", " " + resultPath.getRoute().getTrafast()[0].getPath()[i][1]);
                    }


                    Log.d("Testing Success", " " + t.toString());

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






 /*
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService servicel = retrofit.create(RetrofitService.class);

        Call<PostResult> call = servicel.getPosts("1");

        call.enqueue(new Callback<PostResult>() {
            @Override
            public void onResponse(Call<PostResult> call, Response<PostResult> response) {
                if(response.isSuccessful()){
                    PostResult result = response.body();
                    Log.d("Testing", "onResponse: 성공, 결과 \n" + result.toString());
                }else{
                    Log.d("Testing failed", "onResponse : 실패");
                }
            }

            @Override
            public void onFailure(Call<PostResult> call, Throwable t) {
                Log.d("Failing", "onFailure: " + t.getMessage());
            }
        });

         */
