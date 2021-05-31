package com.example.junggar;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostWriteActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    private EditText get_title;
    private EditText get_content;
    private ImageView get_photo;
    private double position_latitude;
    private double position_longitude;
    Intent position_intent ;
    GeoPoint position;
    int markertype;
    String userid;
    String post_time;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postwrite);

        //작성 시간 저장하기
        SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm a");
        Date time = new Date();
        post_time = timeformat.format(time);

        //갤러리에서 사진 받아오기
        get_photo = (ImageView) findViewById(R.id.Main_photo);
        get_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, 3);
            }
        });

        //유저 id 받아오기
        userid = UserInfoApplication.getInstance().getGlobalUserId();

        TextView btn_submit = (TextView) findViewById(R.id.Btn_submit);
        TextView btn_popup = (TextView) findViewById(R.id.Text_Popup);
        TextView btn_allergy = (TextView) findViewById(R.id.Text_Allergy);
        TextView btn_position = (TextView) findViewById(R.id.Text_position);
        get_title = (EditText) findViewById(R.id.Edit_title);
        get_content = (EditText) findViewById(R.id.Edit_content);

        btn_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String title = get_title.getText().toString().trim();
                final String content = get_content.getText().toString().trim();

                Map<String, Object> post = new HashMap<>();
                post.put("title", title);
                post.put("position",position);
                post.put("id",userid);
                post.put("content",content);
                post.put("markertype",markertype);
                post.put("time",post_time);
                //post.put();


                db.collection("posts").document(title)
                        .set(post)
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

                StorageReference storageRef = storage.getReference().child(title+".jpg");
                get_photo.setDrawingCacheEnabled(true);
                get_photo.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) get_photo.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = storageRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    }
                });

                finish();
            }
        });

        btn_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostWriteActivity.this, MarkerchooseActivity.class);
                startActivityForResult(intent,2);
            }
        });

        btn_allergy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(PostWriteActivity.this, AllergyActivity.class);
                //startActivity(intent);
            }
        });

        btn_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostWriteActivity.this, PositionActivity.class);
                startActivityForResult(intent,1);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 1:
                position_latitude = data.getDoubleExtra("latitude",0.0);
                position_longitude = data.getDoubleExtra("longitude",0.0);
                position = new GeoPoint(position_latitude,position_longitude);
                break;
            case 2:
                markertype = data.getIntExtra("markertype",1);
                break;
            case 3:
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    get_photo.setImageBitmap(img);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }
}
