package com.max.rockSongs.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "rockSongsDB";
    private static int DB_VERSION = 1;
    static final String TABLE = "songs";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_ALBUM = "album";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IS_ARCHIVED = "is_archived";

    public DatabaseHelper(Context context)
    {
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT, "
                + COLUMN_AUTHOR + " TEXT, "
                + COLUMN_ALBUM + " TEXT, "
                + COLUMN_YEAR + " INTEGER, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_IS_ARCHIVED + " INTEGER DEFAULT 0);");
        // добавление начальных данных
        insertInitialValues(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if(newVersion==2 && oldVersion==1)
        {

        }
    }

    public void insertInitialValues(SQLiteDatabase db)
    {
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_AUTHOR  + ", " + COLUMN_ALBUM +
                ", " + COLUMN_YEAR + ", " + COLUMN_DESCRIPTION + " ) " +
                "VALUES ('RockIT','Слонев Максим','КНТ-519', 2021," +
                " 'Эта песня была написана студентом группы КНТ-519');");
    }
}
