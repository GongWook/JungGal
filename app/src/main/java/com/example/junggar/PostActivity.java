package com.example.junggar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.naver.maps.geometry.LatLng;
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
    private ImageView Btn_finish;
    private ImageView post_direction;
    private ImageView post_comment;
    private ImageView Btn_cancle;

    String title;
    String content;
    String userid;
    String gender;
    String name;
    String self_introduce;

    GeoPoint position;
    double longitude;
    double latitude;

    String check_userid;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        Btn_finish = (ImageView) findViewById(R.id.Btn_finish);
        post_direction = (ImageView) findViewById(R.id.post_route);
        post_comment = (ImageView) findViewById(R.id.post_commentwrite);
        Btn_cancle = (ImageView) findViewById(R.id.btn_cancle);

        check_userid = UserInfoApplication.getInstance().getGlobalUserId();
        
        
        //게시글 삭제
        Btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_userid.equals(userid)){
                    db.collection("posts").document(title)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                    db.collection("comment").document(title).collection(content).document().delete();
                                    Toast.makeText(PostActivity.this, "반찬 나눔이 종료되었습니다 !", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //Log.w(TAG, "Error deleting document", e);
                                }
                            });

                    finish();
                }else{
                    Toast.makeText(PostActivity.this, "게시글 작성자가 아닙니다 !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //화면 종료
        Btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //댓글 작성 화면
        post_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this,CommentWritveActivity.class);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });

        //길 찾기 화면
        post_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, DirectionActivity.class);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                startActivity(intent);
            }
        });

        //title 값 받아오기
        Intent intent = getIntent();
        title = intent.getStringExtra("title");

        //DB 내용 받아오기
        Executor executor = command -> {
            Thread thread = new Thread(command);
            thread.start();
        };

        Handler handler = new Handler(Looper.getMainLooper());

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
                        position = (GeoPoint) result.get("position");
                        latitude = position.getLatitude();
                        longitude = position.getLongitude();

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
                                    post_name.setTextSize(15);
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
                    post_title.setText("<  " + title +"  >");
                    post_title.setTextSize(17);
                    post_content.setText(content);
                });
            }
        });

        ImageView imageButton = (ImageView) findViewById(R.id.post_seecomment);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this, CommentActivity.class);
                intent.putExtra("comment",title);
                startActivity(intent);
            }
        }); //댓글 보기 창 화면 이동(수연)
    }

}
