package com.max.rockSongs.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.max.rockSongs.model.Album;
import com.max.rockSongs.model.Author;
import com.max.rockSongs.model.Song;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter
{
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context)
    {
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open()
    {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        dbHelper.close();
    }

    private Cursor getAllEntries()
    {
        String whereClause = "is_archived = ?";
        String[] whereArgs = new String[]{String.valueOf(0)};
        String[] columns = new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_AUTHOR, DatabaseHelper.COLUMN_ALBUM, DatabaseHelper.COLUMN_YEAR,
                DatabaseHelper.COLUMN_DESCRIPTION};
        return database.query(DatabaseHelper.TABLE, columns,
                whereClause, whereArgs, null, null, null);
    }

    public List<Song> getSongs()
    {
        ArrayList<Song> songs = new ArrayList<>();
        Cursor cursor = getAllEntries();
        while (cursor.moveToNext())
        {
            @SuppressLint("Range") long id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            @SuppressLint("Range") String authorName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_AUTHOR));
            @SuppressLint("Range") String albumName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ALBUM));
            @SuppressLint("Range") int albumYear = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_YEAR));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION));
            songs.add(new Song(id, name, new Album(albumName, new Author(authorName), albumYear), description));
        }
        cursor.close();
        return songs;
    }

    public Song getSong(long id)
    {
        Song song = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?", DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst())
        {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            @SuppressLint("Range") String authorName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_AUTHOR));
            @SuppressLint("Range") String albumName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ALBUM));
            @SuppressLint("Range") int albumYear = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_YEAR));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION));
            song = new Song(id, name, new Album(albumName, new Author(authorName), albumYear), description);
        }
        cursor.close();
        return song;
    }

    public long insert(Song song)
    {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, song.getName());
        cv.put(DatabaseHelper.COLUMN_AUTHOR, song.getAlbum().getAuthor().getName());
        cv.put(DatabaseHelper.COLUMN_ALBUM, song.getAlbum().getName());
        cv.put(DatabaseHelper.COLUMN_YEAR, song.getAlbum().getYear());
        cv.put(DatabaseHelper.COLUMN_DESCRIPTION, song.getDescription());
        return database.insert(DatabaseHelper.TABLE, null, cv);
    }

    public long delete(long songId)
    {
        String whereClause = "_id = ?";
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_IS_ARCHIVED, 1);
        String[] whereArgs = new String[]{String.valueOf(songId)};
        return database.update(DatabaseHelper.TABLE, cv, whereClause, whereArgs);
    }

    public long update(Song song){

        String whereClause = DatabaseHelper.COLUMN_ID + "=" + song.getId();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, song.getName());
        cv.put(DatabaseHelper.COLUMN_AUTHOR, song.getAlbum().getAuthor().getName());
        cv.put(DatabaseHelper.COLUMN_ALBUM, song.getAlbum().getName());
        cv.put(DatabaseHelper.COLUMN_YEAR, song.getAlbum().getYear());
        cv.put(DatabaseHelper.COLUMN_DESCRIPTION, song.getDescription());
        return database.update(DatabaseHelper.TABLE, cv, whereClause, null);
    }

}
