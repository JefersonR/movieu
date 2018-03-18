package br.udacity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jeferson on 17/03/2018.
 */

public class MoviesDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "fav_movie.db";
    private static final int DATABASE_VERSION = 2;

    public MoviesDB(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +
                MoviesContract.Movie.NAME+ " ( " +
                MoviesContract.Movie.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MoviesContract.Movie.Cols.MOVIE_ID  + " TEXT UNIQUE," +
                MoviesContract.Movie.Cols.TITLE + " TEXT NOT NULL, " +
                MoviesContract.Movie.Cols.ORIGINAL_TITLE 	+ " TEXT , " +
                MoviesContract.Movie.Cols.RELEASE_DATE + " TEXT, " +
                MoviesContract.Movie.Cols.LANGUAGE + " TEXT, " +
                MoviesContract.Movie.Cols.POSTER + " TEXT, " +
                MoviesContract.Movie.Cols.PLOT + " TEXT, " +
                MoviesContract.Movie.Cols.VOTE_AVERAGE + " DOUBLE, " +
                "UNIQUE (" + MoviesContract.Movie.Cols.ID + ") ON CONFLICT REPLACE)"
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
