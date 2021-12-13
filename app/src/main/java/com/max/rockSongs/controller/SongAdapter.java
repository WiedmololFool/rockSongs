package com.max.rockSongs.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.max.rockSongs.R;
import com.max.rockSongs.model.Song;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends ArrayAdapter<Song> //implements Filterable
{
    private LayoutInflater inflater;
    private int layout;
    private List<Song> songList;
    private List<Song> originalSongList;
    private List<Song> tempSongList;

    public SongAdapter(Context context, int resource, List<Song> songs)
    {
        super(context, resource, songs);
        this.songList = songs;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        originalSongList = new ArrayList<>();
        this.originalSongList.addAll(songList);
        tempSongList = new ArrayList<>();
        this.tempSongList.addAll(songList);

    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = inflater.inflate(this.layout, parent, false);
        TextView tvSongName = view.findViewById(R.id.tvSongName);
        TextView tvSongAuthor = view.findViewById(R.id.tvSongAuthor);
        TextView tvSongAlbum = view.findViewById(R.id.tvSongAlbum);
        TextView tvSongYear = view.findViewById(R.id.tvSongYear);

        Song song = songList.get(position);

        tvSongName.setText(song.getName());
        tvSongAuthor.setText(song.getAlbum().getAuthor().getName());
        tvSongAlbum.setText(song.getAlbum().getName());
        tvSongYear.setText(String.valueOf(song.getAlbum().getYear()));

        return view;
    }

    public void filter(String charName, String charAuthor, String charAlbum, String charYear)
    {
        charName = charName.toLowerCase();
        charAuthor = charAuthor.toLowerCase();
        charAlbum = charAlbum.toLowerCase();
        charYear = charYear.toLowerCase();

        songList.clear();
        if (charName.length() == 0)
        {
            charName = "";
        }
        if (charAuthor.length() == 0)
        {
            charAuthor = "";
        }
        if (charAlbum.length() == 0)
        {
            charAlbum = "";
        }
        if (charYear.length() == 0)
        {
            charYear = "";
        }
        {
            for (Song song : originalSongList)
            {
                if (song.getName().toLowerCase().contains(charName) &&
                        song.getAlbum().getAuthor().getName().toLowerCase().contains(charAuthor) &&
                        song.getAlbum().getName().toLowerCase().contains(charAlbum) &&
                        String.valueOf(song.getAlbum().getYear()).toLowerCase().contains(charYear))
                {
                    songList.add(song);
                }
            }
        }
        notifyDataSetChanged();
    }
}

