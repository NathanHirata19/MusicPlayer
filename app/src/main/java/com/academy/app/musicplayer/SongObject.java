package com.academy.app.musicplayer;

public class SongObject {
    public int songID;
    public String title;
    public String artist;
    public SongObject(int songID, String title, String artist) {
        this.songID = songID;
        this.title = title;
        this.artist = artist;
    }
}
