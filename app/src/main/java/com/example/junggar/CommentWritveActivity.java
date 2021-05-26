package com.example.junggar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CommentWritveActivity extends AppCompatActivity {

    private ImageView back_icon;
    private TextView comment_icon;
    private EditText comment_content;
    String userid;
    String username;
    String title;
    String post_time;
    String content;

    private Map<String, Object> result;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm a");
    Date time = new Date();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_commentwrite);
        
        back_icon = (ImageView) findViewById(R.id.Btn_back);
        comment_icon = (TextView) findViewById(R.id.comment_submit);
        comment_content = (EditText) findViewById(R.id.comment_content);

        //시간 설정
        post_time = timeformat.format(time);
        Log.d("TESTING TIME", post_time);

        //user id setting
        userid = UserInfoApplication.getInstance().getGlobalUserId();

        //title 값 가져오기
        Intent intent = getIntent();
        title = intent.getStringExtra("title");

        //user name 가져오기
        DocumentReference docRef = db.collection("users").document(userid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    result = document.getData();
                    username = (String) result.get("name");
                    if (document.exists()) {
                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        //뒤로가기 버튼
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //댓글 작성 버튼
        comment_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                content = comment_content.getText().toString().trim();

                Map<String, Object> comment = new HashMap<>();
                comment.put("name",username);
                comment.put("id",userid);
                comment.put("title",title);
                comment.put("write_time",post_time);
                comment.put("comment",content);

                db.collection("comment").document(title).collection("userid").document(userid)
                        .set(comment)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //Log.d(TAG, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //Log.w(TAG, "Error writing document", e);
                            }
                        });

                finish();
            }

        });

    }
}
