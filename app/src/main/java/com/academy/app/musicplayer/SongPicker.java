package com.academy.app.musicplayer;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SongPicker extends AppCompatActivity {

    int numOfSongs = SongPicker.songList.size(); //Call the SongPicker Activity, get its songList variable, get its size

    private Button pickSongButtonVar2;
    private Button pickSongButtonVar;

    MediaMetadataRetriever songInfo = new MediaMetadataRetriever();
    Intent launchSongPlayer;
    public String songTitle;

    public String songArtist;

    public int songID;

    public static int[] songIDs;
    public static ArrayList<SongObject> songList ;

    SongAdapter mySongAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_picker);

        pickSongButtonVar = (Button) findViewById(R.id.pS);
        pickSongButtonVar2 = (Button) findViewById(R.id.pS2);


        launchSongPlayer = new Intent(this, MainActivity.class);

        int s1id = R.raw.song1;
        String s1t = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        String s1a = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        SongObject s1 = new SongObject(s1id, s1t,s1a);

        songIDs = new int[5];
        songIDs[0] = R.raw.song1;
        songIDs[1] = R.raw.song2;
        songIDs[2] = R.raw.song3;
        songIDs[3] = R.raw.song4;
        songIDs[4] = R.raw.song5;

        SongAdapter mySongAdapter = new SongAdapter(this, songList);

        for( int i=0;i<songIDs.length;i++) {
            songID = songIDs[i];

            Uri mediaPath = Uri.parse("android.resource://" + getPackageName() + "/" + songID);
            songInfo.setDataSource(this, mediaPath);

            songTitle = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            songArtist = songInfo.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);

            songList.add( new SongObject(songID, songTitle, songArtist) );
        }

        ListView listView = (ListView) findViewById(R.id.songListView);
        listView.setAdapter(mySongAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                launchPlayer(position);
            }
        });


        songPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                //call forward method
            }
        });
    }



    public void playButtonClick(View view){
        playSong();
    }

    private void playSong(){
//All the code that actually plays the song
//the call at the end of onCreate calls this function
    }

    public void launchSong1(View view){
        String songID = String.valueOf(R.raw.song1);
        launchPlayer(songID);
    }

    public void launchSong2(View view){
        String songID = String.valueOf(R.raw.song2);
        launchPlayer(songID);
    }

    public void launchPlayer(String songID){
        Intent launchSongPlayer = new Intent(SongPicker.this, MainActivity.class);
        launchSongPlayer.putExtra("songMessage", songID);
        startActivity(launchSongPlayer);

    }
}
