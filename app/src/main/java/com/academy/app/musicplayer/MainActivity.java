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
    private Handler myHandler = new Handler();

    private MediaPlayer s1;

    private int seekTime;

    MediaMetadataRetriever songInfo = new MediaMetadataRetriever();

    private SeekBar mySongBarVar;

    Button playButtonVar;
    Button pauseButtonVar;
    Button rewind;
    Button forward;
    Button stopButton;

    double startTimeMS;
    double finalTimeMS;
    double currentTimeMS;

    TextView endTimeVar;
    TextView currentTimeVar;
    TextView songTitleView;
    TextView songArtistView;

    String songTitle;
    String songArtist;

    int endMinutes;
    int endSeconds;
    int currentMinutes;
    int currentSeconds;



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

        mySongBarVar.setMax((int) finalTimeMS);
        mySongBarVar.setProgress((int) currentTimeMS);

        startTimeMS = (int) (finalTimeMS - finalTimeMS);
        endMinutes = (int) (finalTimeMS / 1000 / 60);
        endSeconds = ((int) (finalTimeMS / 1000)) %60;
        currentMinutes =(int) (currentTimeMS/1000/60);
        currentSeconds = ((int)(currentTimeMS/1000)) %60;

        songArtistView.setText(songArtist);
        songTitleView.setText(songTitle);

        endTimeVar.setText(endMinutes + ":" + endSeconds);
        currentTimeVar.setText(currentMinutes + ":" + currentSeconds);


        finalTimeMS = s1.getDuration();
        currentTimeMS = s1.getCurrentPosition();

        myHandler.postDelayed(UpdateSongTime, 100);


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

        s1.seekTo( (int) (currentTimeMS - 5000) );
    }

    public void forward(View view){

        s1.seekTo( (int) (currentTimeMS + 5000) );
    }
    public void stopButton(View view){
        s1.seekTo( (int) (currentTimeMS + 5000) );
        }

}
