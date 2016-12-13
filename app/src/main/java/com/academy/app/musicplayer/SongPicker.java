package com.academy.app.musicplayer;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SongPicker extends AppCompatActivity {

    private Button pickSongButtonVar2;
    private Button pickSongButtonVar;
    MediaMetadataRetriever songInfo = new MediaMetadataRetriever();
    MediaMetadataRetriever songInfo2 = new MediaMetadataRetriever();
    Intent launchSongPlayer;
    String songTitle;
    String songTitle2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_picker);

        pickSongButtonVar = (Button) findViewById(R.id.pS);
        pickSongButtonVar2 = (Button) findViewById(R.id.pS2);

        Uri mediaPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.song1);
        songInfo.setDataSource(this, mediaPath);

        Uri mediaPath2 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.song2);
        songInfo2.setDataSource(this, mediaPath2);

        songTitle = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        songTitle2 = songInfo2.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

        pickSongButtonVar.setText(songTitle);
        pickSongButtonVar2.setText(songTitle2);

        launchSongPlayer = new Intent(this, MainActivity.class);
    }
    public void pickSong() {
        pickSongButtonVar.setEnabled(true);
        startActivity(launchSongPlayer);
    }

    public void launchSong1(View view){
        pickSongButtonVar.setEnabled(true);
        String songID = String.valueOf(R.raw.song1);
        launchPlayer(songID);
    }

    public void launchSong2(View view){
        pickSongButtonVar.setEnabled(true);
        String songID = String.valueOf(R.raw.song2);
        launchPlayer(songID);
    }

    public void launchPlayer(String songID){
        Intent launchSongPlayer = new Intent(SongPicker.this, MainActivity.class);
        String message = String.valueOf(R.raw.song1);
        launchSongPlayer.putExtra("songMessage", message);
        startActivity(launchSongPlayer);

    }
}
