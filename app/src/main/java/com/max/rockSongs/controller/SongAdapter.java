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

    public SongAdapter(Context context, int resource, List<Song> songs)
    {
        super(context, resource, songs);
        this.songList = songs;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        originalSongList = new ArrayList<>();
        this.originalSongList.addAll(songList);
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

    public void filterName(String charText) {
        charText = charText.toLowerCase();
        songList.clear();
        if (charText.length() == 0) {
            songList.addAll(originalSongList);
        } else {
            for (Song song : originalSongList) {
                if(song.getName().toLowerCase().contains(charText)){
                    songList.add(song);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void filterAuthor(String charText) {
        charText = charText.toLowerCase();
        songList.clear();
        if (charText.length() == 0) {
            songList.addAll(originalSongList);
        } else {
            for (Song song : originalSongList) {
                if(song.getAlbum().getAuthor().getName().toLowerCase().contains(charText)){
                    songList.add(song);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void filterAlbum(String charText) {
        charText = charText.toLowerCase();
        songList.clear();
        if (charText.length() == 0) {
            songList.addAll(originalSongList);
        } else {
            for (Song song : originalSongList) {
                if(song.getAlbum().getName().toLowerCase().contains(charText)){
                    songList.add(song);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void filterYear(String charText) {
        charText = charText.toLowerCase();
        songList.clear();
        if (charText.length() == 0) {
            songList.addAll(originalSongList);
        } else {
            for (Song song : originalSongList) {
                if( String.valueOf(song.getAlbum().getYear()).toLowerCase().contains(charText)){
                    songList.add(song);
                }
            }
        }
        notifyDataSetChanged();
    }

}

