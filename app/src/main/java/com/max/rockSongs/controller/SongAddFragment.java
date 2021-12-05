package com.max.rockSongs.controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.max.rockSongs.R;
import com.max.rockSongs.database.DatabaseAdapter;
import com.max.rockSongs.model.Album;
import com.max.rockSongs.model.Author;
import com.max.rockSongs.model.Song;


public class SongAddFragment extends Fragment
{
    private EditText nameBox, authorBox, albumBox, yearBox, descriptionBox;
    private Button saveButton, delButton;
    private DatabaseAdapter dbAdapter;
    private TextView headerLabel;

    private long songId = 0;

    public SongAddFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song_add, container, false);
        Fragment listFragment = new ListFragment();
        nameBox = view.findViewById(R.id.addName);
        authorBox = view.findViewById(R.id.addAuthor);
        albumBox = view.findViewById(R.id.addAlbum);
        yearBox = view.findViewById(R.id.addYear);
        descriptionBox = view.findViewById(R.id.addDescription);
        saveButton = view.findViewById(R.id.saveButton);
        delButton = view.findViewById(R.id.deleteButton);
        headerLabel = view.findViewById(R.id.headerLabel);
        dbAdapter = new DatabaseAdapter(getContext());

        Bundle args = getArguments();

        if (args != null)
        {
            songId = getArguments().getLong("id");
        }

        // если 0, то добавление
        if (songId > 0)
        {
            headerLabel.setText(R.string.header_edit_song);
            // получаем элемент по id из бд
            dbAdapter.open();
            Song song = dbAdapter.getSong(songId);
            nameBox.setText(song.getName());
            authorBox.setText(song.getAlbum().getAuthor().getName());
            albumBox.setText(song.getAlbum().getName());
            yearBox.setText(String.valueOf(song.getAlbum().getYear()));
            descriptionBox.setText(song.getDescription());
            dbAdapter.close();
        } else
        {
            // скрываем кнопку удаления
            headerLabel.setText(R.string.header_add_song);
            delButton.setVisibility(View.GONE);
        }


            saveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (!nameBox.getText().toString().equals("") && !authorBox.getText().toString().equals("")  && !(yearBox.getText().toString().equals("")) && !albumBox.toString().equals(""))
                    {
                    String name = nameBox.getText().toString();
                    String authorName = authorBox.getText().toString();
                    String albumName = albumBox.getText().toString();
                    int albumYear = Integer.parseInt(yearBox.getText().toString());
                    String description = descriptionBox.getText().toString();
                    Song song = new Song(songId, name, new Album(albumName, new Author(authorName), albumYear), description);

                    dbAdapter.open();

                    if (songId > 0)
                    {
                        dbAdapter.update(song);
                    } else
                    {
                        dbAdapter.insert(song);
                    }
                    dbAdapter.close();
                    goHome(listFragment);
                    } else
                    {
                        Toast toast =  Toast.makeText(getContext(), R.string.add_song_field_validation,Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            });


        delButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dbAdapter.open();
                dbAdapter.delete(songId);
                dbAdapter.close();
                goHome(listFragment);
            }
        });

        return view;
    }

    private void goHome(Fragment fragment)
    {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .commit();
    }

}