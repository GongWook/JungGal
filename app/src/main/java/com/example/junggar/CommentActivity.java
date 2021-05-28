package com.example.junggar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class CommentActivity extends AppCompatActivity {
    String title; //글 제목

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CommentAdapter adapter;
    ArrayList<CommentModel> itemList;

    private Map<String, Object> result;
    FirebaseFirestore db = FirebaseFirestore.getInstance(); //파이어 베이스 변수 설정

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        recyclerView=findViewById(R.id.recyclerview2);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        title = intent.getStringExtra("comment");
        Log.d("Testing", title);

        //Firebase에서 작성글 내용 들고오기
        itemList = new ArrayList<>();
        db.collection("comment").document(title).collection("content")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            itemList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                result = document.getData();
                                Log.d("Testing", ""+result);


                                itemList.add(new CommentModel(R.drawable.userid_icon,(String)result.get("name"),(String)result.get("comment")));

                                // Log.d("Testing", ""+tmp);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            // Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });





        adapter=new CommentAdapter(itemList);
        recyclerView.setAdapter(adapter);

    }
}
