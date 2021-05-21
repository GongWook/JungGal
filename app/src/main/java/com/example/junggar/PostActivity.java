package com.example.junggar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.naver.maps.map.overlay.Marker;

import java.util.Map;
import java.util.concurrent.Executor;

public class PostActivity extends AppCompatActivity {

    private Map<String, Object> result;

    String title;
    String content;
    String userid;
    private TextView post_title;
    private TextView post_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        post_title = (TextView) findViewById(R.id.post_title);
        post_content = (TextView) findViewById(R.id.post_contents);

        //title 값 받아오기
        Intent intent = getIntent();
        title = intent.getStringExtra("title");

        //DB 내용 받아오기
        Executor executor = command -> {
            Thread thread = new Thread(command);
            thread.start();
        };

        Handler handler = new Handler(Looper.getMainLooper());
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("posts").document(title);
        docRef.get().addOnCompleteListener(executor, new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        result = document.getData();
                        content = (String) result.get("content");

                    } else {
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }

                handler.post(()->{
                    post_title.setText(title);
                    post_content.setText(content);
                });
            }
        });


        //내용 setting


    }
}
