package com.example.junggar;

import android.os.Bundle;
import android.util.Log;

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
    RecyclerView.Adapter adapter;

    private Map<String, Object> result;
    String tmp;
    ArrayList<String> StringArrayList = new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance(); //파이어 베이스 변수 설정

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView=findViewById(R.id.recyclerView);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Firebase에서 작성글 내용 들고오기
        db.collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                result = document.getData();
                                Log.d("Testing", ""+result);
                                tmp = (String) result.get("title");
                                StringArrayList.add(tmp);
                               // Log.d("Testing", ""+tmp);
                            }
                        } else {
                           // Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });




        String[] search_text1={"종욱이네 떡볶이","동욱이네 멸치반찬"};
        String[] search_text2={"6~8시 나눔","2~3시 나눔"};

        adapter=new SearchAdapter(search_text1,search_text2);

        recyclerView.setAdapter(adapter);

    }
}
