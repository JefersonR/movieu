package br.udacity.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Created by Jeferson on 17/03/2018.
 */

public class MoviesContentProvider extends ContentProvider {
    private MoviesDB movieDB;

    @Override
    public boolean onCreate() {
        Context ctx = getContext();
        movieDB = new MoviesDB(ctx);
        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int match = MoviesContract.URI_MATCHER.match(uri);
        switch(match){
            case MoviesContract.Movie.PATH_TOKEN:
                return MoviesContract.Movie.CONTENT_TYPE_DIR;
            case MoviesContract.Movie.PATH_FOR_ID_TOKEN:
                return MoviesContract.Movie.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException ("URI " + uri + " is not supported.");
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase db = movieDB.getWritableDatabase();
        int token = MoviesContract.URI_MATCHER.match(uri);
        switch(token){
            case MoviesContract.Movie.PATH_TOKEN:{
                long id = db.insert(MoviesContract.Movie.NAME, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return MoviesContract.Movie.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }
            default: {
                throw new UnsupportedOperationException("URI: " + uri + " not supported.");
            }
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = movieDB.getReadableDatabase();
        final int match = MoviesContract.URI_MATCHER.match(uri);
        switch(match){
            // retrieve restaurant list
            case MoviesContract.Movie.PATH_TOKEN:{
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(MoviesContract.Movie.NAME);
                return builder.query(db, null, null, null, null, null, null);
            }
            default: return null;
        }
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }
}