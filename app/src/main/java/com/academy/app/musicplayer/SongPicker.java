package com.academy.app.musicplayer;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class SongPicker extends AppCompatActivity {

    private Button pickSongButtonVar;
    MediaMetadataRetriever songInfo = new MediaMetadataRetriever();
    Intent launchSongPlayer;
    String songTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_picker);

        pickSongButtonVar = (Button) findViewById(R.id.pS);

        Uri mediaPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.song1);
        songInfo.setDataSource(this, mediaPath);

        songTitle = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        pickSongButtonVar.setText(songTitle);

        launchSongPlayer = new Intent(this, MainActivity.class);
    }
    public void pickSong() {
        pickSongButtonVar.setEnabled(true);
        startActivity(launchSongPlayer);
    }
}
