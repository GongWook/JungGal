package com.example.junggar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.internal.InternalTokenProvider;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private EditText email_login;
    private EditText pwd_login;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView registerbutton = findViewById(R.id.register_button);
        Button tmp_btn = (Button) findViewById(R.id.Btn_tmp);
        Button test_yong = (Button) findViewById(R.id.Btn_dongtest);

        email_login = (EditText) findViewById(R.id.Login_id);
        pwd_login = (EditText) findViewById(R.id.Login_password);
        login = (Button) findViewById(R.id.Btn_login);
        firebaseAuth = firebaseAuth.getInstance(); //firebaseAuth의 인스턴스르 가져온다.

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        tmp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SearchActivity.class );
                startActivity(intent);
            }
        });
/*
        test_yong.setOnClickListener(new View.OnClickListener() {             // 동욱 테스트용 나중에 지우겠습니당
            @Override                                                         // 동욱씨 firestoreadapter class 내용으로 인해 activity 제거와
            public void onClick(View v) {                                     // intent 화면 연동 주석 처리 해 놓겠습니다.
                Intent intent = new Intent(LoginActivity.this, QuizpageActivity.class );
                startActivity(intent);
            }
        });


 */
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_login.getText().toString().trim();
                String pwd = pwd_login.getText().toString().trim();

                firebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //성공했을 때
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            //실패했을 때
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}

