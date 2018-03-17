package br.udacity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jeferson on 17/03/2018.
 */

public class MoviesDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "fav_restaurnt.db";
    private static final int DATABASE_VERSION = 2;

    public MoviesDB(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +
                MoviesContract.Movie.NAME+ " ( " +
                MoviesContract.Movie.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MoviesContract.Movie.Cols.NAME + " TEXT NOT NULL, " +
                MoviesContract.Movie.Cols.ADDRESS 	+ " TEXT , " +
                MoviesContract.Movie.Cols.CITY + " TEXT, " +
                MoviesContract.Movie.Cols.STATE + " TEXT, " +
                MoviesContract.Movie.Cols.ZIP + " TEXT, " +
                "UNIQUE (" +
                MoviesContract.Movie.Cols.ID +
                ") ON CONFLICT REPLACE)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.Movie.NAME);
            onCreate(db);
        }
    }

}
