package com.academy.app.musicplayer;

import android.content.Context;
import android.content.Intent;
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

    MediaMetadataRetriever songInfo = new MediaMetadataRetriever();

    private SeekBar mySongBarVar;

    public int songNumber;

    public int seekTime;

    public Button playButtonVar;
    public Button pauseButtonVar;
    public Button rewind;
    public Button forward;
    public Button stopButton;

    public double startSeconds;
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

    Intent launchSongPlayer;
    String songID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent thisIntent = getIntent();

        songNumber = Integer.parseInt( thisIntent.getStringExtra("songMessage"));
        thisSong = MainActivity.songList.get(songNumber);
// use thisSong.songID to get the ID number for the song for the MediaPlayer and Metadata
// use thisSong.artist and thisSong.title to get the artist and title now

        MediaPlayer.create(this, Integer.parseInt(songID));

        Uri mediaPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.song1);
        songInfo.setDataSource(this, mediaPath);

        songTitle = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        songArtist = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);


        mySongBarVar= (SeekBar) findViewById(R.id.mySongBar);
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

        startSeconds = 0;
        finalTimeMS = s1.getDuration();

        songArtistView.setText(songArtist);
        songTitleView.setText(songTitle);

        myHandler.postDelayed(UpdateSongTime, 100);

        mySongBarVar.setMax((int) finalTimeMS);
        mySongBarVar.setProgress((int) currentTimeMS);

        mySongBarVar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                seekTime=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                s1.seekTo( seekTime);
                currentTimeMS = seekTime;
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

    public void playSongNow() {
        Toast.makeText(getApplicationContext(), "Playing music", Toast.LENGTH_SHORT).show();
        songPlayer.start();
        finalTimeMS = songPlayer.getDuration();
        currentTimeMS = songPlayer.getCurrentPosition();
        seekbar.setMax((int) finalTimeMS);

        int endMinutes = (int) (finalTimeMS / 1000 / 60);
        int endSeconds = ((int) (finalTimeMS / 1000)) %60;
        endTimeVar.setText(endMinutes + " min, "+ endSeconds+" sec");

        int currentMinutes =(int) (currentTimeMS/1000/60);
        int currentSeconds = ((int)(currentTimeMS/1000)) %60;
        currentTimeVar.setText(currentMinutes + " min, "+ startSeconds+" sec");

        seekbar.setProgress((int) currentTimeMS);
        myHandler.postDelayed(UpdateSongTime, 100);
        stopButton.setEnabled(true);
        pauseButtonVar.setEnabled(true);
        playButtonVar.setEnabled(false);
    }

    private void playNewSong(int position){
        thisSong = MainActivity.songList.get(position);
        songTitle = thisSong.title;
        songArtist = thisSong.artist;

        songTitleView.setText(songTitle);
        songArtistView.setText(songArtist);
//in this version, I called my MediaPlayer songPlayer
        songPlayer.stop();
        songPlayer= MediaPlayer.create(this, thisSong.songID);
        songPlayer.seekTo(0);

        playSongNow();
    }


}
