package com.example.junggar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.naver.maps.map.overlay.Marker;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.concurrent.Executor;

public class PostActivity extends AppCompatActivity {

    private Map<String, Object> result;
    private Map<String, Object> user_info_result;


    private TextView post_title;
    private TextView post_content;
    private ImageView post_img;
    private TextView post_name;
    private TextView post_gender;
    private TextView post_selfinfo;

    String title;
    String content;
    String userid;
    String gender;
    String name;
    String self_introduce;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        post_title = (TextView) findViewById(R.id.post_title);
        post_content = (TextView) findViewById(R.id.post_contents);
        post_img = (ImageView) findViewById(R.id.main_img);
        post_name = (TextView) findViewById(R.id.post_username);
        post_gender = (TextView) findViewById(R.id.post_usergender);
        post_selfinfo = (TextView) findViewById(R.id.post_introduce);

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
        FirebaseStorage storage = FirebaseStorage.getInstance();

        //사진 들고오기
        StorageReference storageRef = storage.getReference().child(title+".jpg");
        final long ONE_MEGABYTE = 1024 * 1024;
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(executor,new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                handler.post(()->{
                    post_img.setImageBitmap(bitmap);
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


        //글 제목과, 글 내용 들고오기
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
                        userid = (String) result.get("id");

                        //작성자 이름, 성별, 간략한 자기소개
                        DocumentReference docRef2 = db.collection("users").document(userid);
                        docRef2.get().addOnCompleteListener(executor, new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                        user_info_result = document.getData();
                                        name = (String) user_info_result.get("name");
                                        gender = (String) user_info_result.get("gender");
                                        self_introduce = (String) user_info_result.get("self_introduce");

                                    } else {
                                        //Log.d(TAG, "No such document");
                                    }
                                } else {
                                    //Log.d(TAG, "get failed with ", task.getException());
                                }

                                handler.post(()->{
                                    post_name.setText(name);
                                    post_gender.setText(gender);
                                    post_selfinfo.setText(self_introduce);
                                });
                            }
                        });

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





    }
}
