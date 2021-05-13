package com.example.junggar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class MarkerchooseActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    int choose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markerchoose);

        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeLister);
        Button button = (Button) findViewById(R.id.Btn_setpopup);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("markertype",choose);
                setResult(2,intent);
                finish();
            }
        });


        }

    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeLister = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.radiobtn_chicken:
                    choose =1;
                    break;
                case R.id.radiobtn_westfood:
                    choose =2;
                    break;
                case R.id.radiobtn_korfood:
                    choose =3;
                    break;
                case R.id.radiobtn_japanfood:
                    choose =4;
                    break;
                case R.id.radiobtn_fish:
                    choose =5;
                    break;
                case R.id.radiobtn_noodle:
                    choose =6;
                    break;
                case R.id.radiobtn_snackfood:
                    choose =7;
                    break;
            }
        }
    };
}