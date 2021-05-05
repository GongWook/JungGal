package com.example.junggar;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText name_join;
    private EditText email_join;
    private EditText pwd_join;
    private EditText pwd_check;
    private RadioGroup gender_check;
    private String Gender;
    private EditText self_introduce;
    private Button btn;

    FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name_join = (EditText) findViewById(R.id.Edit_nickname);
        email_join = (EditText) findViewById(R.id.Edit_id);
        pwd_join = (EditText) findViewById(R.id.Edit_password);
        pwd_check = (EditText) findViewById(R.id.Edit_passwordchk);
        gender_check = (RadioGroup) findViewById(R.id.Gender_radioGroup);
        self_introduce = (EditText) findViewById(R.id.Edit_introduce);
        btn = (Button) findViewById(R.id.Btn_register);

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        gender_check.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.male){
                    Gender = "Male";
                }else{
                    Gender = "Female";
                }
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = name_join.getText().toString().trim();
                final String email = email_join.getText().toString().trim();
                final String pwd = pwd_join.getText().toString().trim();
                final String pwdcheck = pwd_check.getText().toString().trim();
                final String introduce = self_introduce.getText().toString().trim();


                if(pwd.equals(pwdcheck)){
                    firebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                Map<String, Object> user = new HashMap<>();
                                user.put("id",email);
                                user.put("name", name);
                                user.put("gender", Gender);
                                user.put("self_introduce", introduce);

                                db.collection("users").document(email)
                                        .set(user)
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


                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class );
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(RegisterActivity.this, "등록 에러", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this, "비밀번호가 동일하지 않습니다",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
