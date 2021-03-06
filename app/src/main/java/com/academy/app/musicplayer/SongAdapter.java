package com.academy.app.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter<SongObject> {

    private Context context;
    private ArrayList<SongObject> values;

    @Override
    public View getView(int position, View songView, ViewGroup parent) {
        SongObject thisSong = getItem(position);
        TextView songNameViewVar= (TextView) songView.findViewById(R.id.songTitle);
        TextView songArtistViewVar= (TextView) songView.findViewById(R.id.songArtist);
        songNameViewVar.setText(thisSong.songTitle);
        songArtistViewVar.setText(thisSong.songArtist);



        if (songView == null) {
            songView = LayoutInflater.from(getContext()).inflate( R.layout.song_list_item  , parent, false);

        }

        return songView;
    }

    public SongAdapter(Context context, ArrayList<SongObject> values){
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }
    }




