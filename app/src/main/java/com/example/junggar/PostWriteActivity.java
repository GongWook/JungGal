package com.example.junggar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class PostWriteActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private EditText get_title;
    private double position_latitude;
    private double position_longitude;
    Intent position_intent ;
    GeoPoint position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postwrite);

        TextView btn_submit = (TextView) findViewById(R.id.Btn_submit);
        TextView btn_popup = (TextView) findViewById(R.id.Text_Popup);
        TextView btn_allergy = (TextView) findViewById(R.id.Text_Allergy);
        TextView btn_position = (TextView) findViewById(R.id.Text_position);
        get_title = (EditText) findViewById(R.id.Edit_title);

        btn_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String title = get_title.getText().toString().trim();

                Map<String, Object> post = new HashMap<>();
                post.put("title", title);
                post.put("position",position);
                //post.put();


                db.collection("posts")
                        .add(post)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                //Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //Log.w(TAG, "Error adding document", e);
                            }
                        });

                finish();
            }
        });

        btn_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostWriteActivity.this, MarkerchooseActivity.class);
                startActivity(intent);
            }
        });

        btn_allergy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostWriteActivity.this, AllergyActivity.class);
                startActivity(intent);
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
                break;
            case 3:
                break;
        }

    }
}
