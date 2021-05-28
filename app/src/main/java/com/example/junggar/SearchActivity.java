package com.example.junggar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.overlay.Marker;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executor;

public class SearchActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter adapter;
    ArrayList<itemModel> itemList;

    int image; //마커 이미지

    private Map<String, Object> result;
    FirebaseFirestore db = FirebaseFirestore.getInstance(); //파이어 베이스 변수 설정


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soo_yeon_activity_searchtmp);

        recyclerView=findViewById(R.id.recyclerview);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        //Firebase에서 작성글 내용 들고오기
        itemList = new ArrayList<>();
        db.collection("posts").orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            itemList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                result = document.getData();
                                Log.d("Testing", ""+result);

                                int markertype = Integer.parseInt(String.valueOf(result.get("markertype")));
                                Log.d("Testing",""+result.get("markertype"));

                                    switch (markertype){
                                        case 1:
                                            image =R.drawable.hen_custom;
                                            break;
                                        case 2:
                                            image =R.drawable.westfood_custom;
                                            break;
                                        case 3:
                                            image =R.drawable.koreanfood_custom;
                                            break;
                                        case 4:
                                            image =R.drawable.japanfood_custom;
                                            break;
                                        case 5:
                                            image =R.drawable.fish_custom;
                                            break;
                                        case 6:
                                            image =R.drawable.noodle_custom;
                                            break;
                                        case 7:
                                            image =R.drawable.snackfood_custom;
                                            break;
                                    }



                                itemList.add(new itemModel(image,(String)result.get("title"),(String)result.get("content"),(String)result.get("time")));

                                Log.d("Testing", ""+itemList);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            // Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        adapter=new SearchAdapter(itemList);
        recyclerView.setAdapter(adapter);

        //뒤로 가기 버튼 -> 메인화면
        ImageView imageButton = (ImageView) findViewById(R.id.search_back);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
