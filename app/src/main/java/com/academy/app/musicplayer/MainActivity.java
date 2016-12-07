package com.academy.app.musicplayer;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public Handler myHandler = new Handler();

    private MediaPlayer s1;

    private int seekTime;

    MediaMetadataRetriever songInfo = new MediaMetadataRetriever();

    private SeekBar mySongBarVar;

    public Button playButtonVar;
    public Button pauseButtonVar;
    public Button rewind;
    public Button forward;
    public Button stopButton;

    public double startTimeMS;
    public double finalTimeMS;
    public double currentTimeMS;

    public TextView endTimeVar;
    public TextView currentTimeVar;
    public TextView songTitleView;
    public TextView songArtistView;

    public String songTitle;
    public String songArtist;

    public int endMinutes;
    public int endSeconds;
    public int currentMinutes;
    public int currentSeconds;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Uri mediaPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.song1);
        songInfo.setDataSource(this, mediaPath);

        songTitle = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        songArtist = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);



        songTitleView = (TextView) findViewById(R.id.songT);
        songArtistView = (TextView) findViewById(R.id.songA);
        s1 = MediaPlayer.create(this,R.raw.song1);
        playButtonVar = (Button) findViewById(R.id.button2);
        pauseButtonVar = (Button) findViewById(R.id.button1);
        forward = (Button) findViewById(R.id.forward);
        stopButton = (Button) findViewById(R.id.stopb);
        rewind = (Button) findViewById(R.id.rewind);
        endTimeVar = (TextView) findViewById(R.id.eT);
        currentTimeVar = (TextView) findViewById(R.id.cT);
        mySongBarVar= (SeekBar) findViewById(R.id.mySongBar);

        startTimeMS = 0;
        finalTimeMS = s1.getDuration();
        endMinutes = (int) (finalTimeMS / 1000 / 60);
        endSeconds = ((int) (finalTimeMS / 1000)) %60;

        songArtistView.setText(songArtist);
        songTitleView.setText(songTitle);

        endTimeVar.setText(endMinutes + ":" + endSeconds);


        myHandler.postDelayed(UpdateSongTime, 100);

        mySongBarVar.setMax((int) finalTimeMS);
        mySongBarVar.setProgress((int) currentTimeMS);


        mySongBarVar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekTime=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                s1.seekTo( seekTime);
                startTimeMS = seekTime;
            }
        });
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            currentTimeMS = s1.getCurrentPosition();
            currentMinutes =(int) (currentTimeMS/1000/60);
            currentSeconds = ((int)(currentTimeMS/1000)) %60;
            currentTimeVar.setText(currentMinutes + ":" + currentSeconds);


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
    public void rewind(View view){
        Context context = getApplicationContext();
        CharSequence text = "Rewinded";
        int duration = Toast.LENGTH_SHORT;
        Toast myMessage= Toast.makeText(context, text, duration);
        myMessage.show();
        s1.seekTo( (int) (currentTimeMS - 5000) );
    }

    public void forward(View view){
        Context context = getApplicationContext();
        CharSequence text = "Forwarded";
        int duration = Toast.LENGTH_SHORT;
        Toast myMessage= Toast.makeText(context, text, duration);
        myMessage.show();
        s1.seekTo( (int) (currentTimeMS + 5000) );
    }
    public void stopButton(View view){
        Context context = getApplicationContext();
        CharSequence text = "Stopped";
        int duration = Toast.LENGTH_SHORT;
        Toast myMessage= Toast.makeText(context, text, duration);
        myMessage.show();
        pauseButtonVar.setEnabled(false);
        playButtonVar.setEnabled(true);
        s1.seekTo(0);
        s1.pause();
        }

}
