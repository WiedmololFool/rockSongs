package com.max.rockSongs.controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.max.rockSongs.R;
import com.max.rockSongs.database.DatabaseAdapter;
import com.max.rockSongs.model.Song;

import java.util.List;


public class ListFragment extends Fragment
{

    public ListFragment()
    {
        // Required empty public constructor
    }

    SongAdapter songAdapter;
    private ListView songsList;
    private Button btnAddSong, btnClearFilter;
    private EditText nameFilter, authorFilter, albumFilter, yearFilter;
    private static final String TAG = "MyApp";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        Fragment songAddFragment = new SongAddFragment();
        songsList = view.findViewById(R.id.songsList);
        btnAddSong = view.findViewById(R.id.btnAddSong);
        btnClearFilter = view.findViewById(R.id.btnClearFilter);
        nameFilter = view.findViewById(R.id.nameFilter);
        authorFilter = view.findViewById(R.id.authorFilter);
        albumFilter = view.findViewById(R.id.albumFilter);
        yearFilter = view.findViewById(R.id.yearFilter);


        songsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                long id = songAdapter.getItem(i).getId();
                Bundle args = new Bundle();
                args.putLong("id", id);
                Log.i(TAG, String.valueOf(args.getLong("id")));
                songAddFragment.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_view,  songAddFragment )
                        .addToBackStack(null)
                        .commit();
                return false;
            }
        });

        songsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                String selectedItem = songAdapter.getItem(i).getName();
                String description = songAdapter.getItem(i).getDescription();
                CustomDialogFragment dialog = new CustomDialogFragment();
                Bundle args = new Bundle();
                args.putString("song", selectedItem);
                args.putString("description", description);
                dialog.setArguments(args);
                dialog.show(getActivity().getSupportFragmentManager(), "custom");

            }
        });

        btnAddSong.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_view, songAddFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnClearFilter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                nameFilter.setText("");
                authorFilter.setText("");
                albumFilter.setText("");
                yearFilter.setText("");
            }
        });
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        DatabaseAdapter dbAdapter = new DatabaseAdapter(getContext());
        dbAdapter.open();
        List <Song> songs = dbAdapter.getSongs();

        songAdapter = new SongAdapter(getContext(), R.layout.custom_list_item, songs );

        songsList.setAdapter(songAdapter);
        dbAdapter.close();


        nameFilter.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                songAdapter.filterName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        authorFilter.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                songAdapter.filterAuthor(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        albumFilter.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                songAdapter.filterAlbum(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        yearFilter.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                songAdapter.filterYear(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
    }
}