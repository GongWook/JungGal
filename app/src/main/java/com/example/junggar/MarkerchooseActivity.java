package com.example.junggar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MarkerchooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markerchoose);

        final RadioGroup rg = (RadioGroup)findViewById(R.id.radiogroup1);
        Button b = (Button)findViewById(R.id.btn_popupok);
        final TextView tv = (TextView)findViewById(R.id.text_result);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int id = rg.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(id);
                tv.setText(rb.getText().toString());
            }
        }); //실행이 안 됩니다ㅠㅠ
    }

    //public void createNotification(View view) {
    //        show();
    //    }
    //
    //    private void show() {
    //        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"default");
    //
    //        builder.setSmallIcon(R.mipmap.ic_launcher);
    //        builder.setContentTitle("알림 제목");
    //        builder.setContentText("알림 세부 텍스트");
    //
    //        Intent intent=new Intent(this, MainActivity.class);
    //        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    //
    //        builder.setContentIntent(pendingIntent);
    //
    //        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
    //        builder.setLargeIcon(largeIcon);
    //
    //        builder.setColor(Color.RED);
    //
    //        Uri ringtoneUri= RingtoneManager.getActualDefaultRingtoneUri(this,RingtoneManager.TYPE_NOTIFICATION);
    //        builder.setSound(ringtoneUri);
    //
    //        long[] vibrate = {0,100,200,300};
    //        builder.setVibrate(vibrate);
    //        builder.setAutoCancel(true);
    //
    //        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    //            manager.createNotificationChannel(new NotificationChannel("default","기본 채널",NotificationManager.IMPORTANCE_DEFAULT));
    //        }
    //        manager.notify(1,builder.build());
    //    }
    //
    //    public void removeNotification(View view) {
    //        hide();
    //    }
    //
    //    private void hide() {
    //        NotificationManagerCompat.from(this).cancel(1);
    //    } 버튼을 통해 상단바 알림 생성 구현
}