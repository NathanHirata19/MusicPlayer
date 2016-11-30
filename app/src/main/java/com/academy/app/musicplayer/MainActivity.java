package com.academy.app.musicplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Handler myHandler = new Handler();

    private MediaPlayer s1;
    Button playButtonVar;
    Button pauseButtonVar;
    int finalTimeMS;
    int currentTimeMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        s1 = MediaPlayer.create(this,R.raw.song1);
        playButtonVar = (Button) findViewById(R.id.button2);
        pauseButtonVar = (Button) findViewById(R.id.button1);

        endMinutes = (int) (finalTimeMS / 1000 / 60);
        endSeconds = ((int) (finalTimeMS / 1000)) %60;
        currentMinutes =(int) (currentTimeMS/1000/60);
        currentSeconds = ((int)(currentTimeMS/1000)) %60;

        finalTimeMS = s1.getDuration();
        currentTimeMS = s1.getCurrentPosition();

        endTimeViewVar.setText("Text");
        myHandler.postDelayed(UpdateSongTime, 100);
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            myHandler.postDelayed(this, 100);
        }
    };

    public void playButton(View view){
        pauseButtonVar.setEnabled(true);
        playButtonVar.setEnabled(false);
        s1.start();
    }
    public void pauseButton(View view){
        Context context = getApplicationContext();
        CharSequence text = "Paused";
        int duration = Toast.LENGTH_SHORT;
        Toast myMessage= Toast.makeText(context, text, duration);
        myMessage.show();
        s1.pause();
        pauseButtonVar.setEnabled(false);
        playButtonVar.setEnabled(true);
    }
}
