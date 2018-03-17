package br.udacity.data;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Jeferson on 17/03/2018.
 */

public class MoviesContract {
    public static final String AUTHORITY = "udacity.contentprovider.movie";
    private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final UriMatcher URI_MATCHER = buildUriMatcher();

    private MoviesContract(){};

    private static  UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = AUTHORITY;

        matcher.addURI(authority, Movie.PATH, Movie.PATH_TOKEN);
        matcher.addURI(authority, Movie.PATH_FOR_ID, Movie.PATH_FOR_ID_TOKEN);

        return matcher;
    }

    public static class Movie {
        public static final String NAME = "movie";

        public static final String PATH = "movies";
        public static final int PATH_TOKEN = 100;
        public static final String PATH_FOR_ID = "movies/*";
        public static final int PATH_FOR_ID_TOKEN = 200;

        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH).build();

        public static final String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.favmovie.app";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.favmovie.app";

        public static class Cols {
            public static final String ID = BaseColumns._ID; // convention
            public static final String NAME = "movie_name";
            public static final String ADDRESS  = "movie_addr";
            public static final String CITY = "movie_city";
            public static final String STATE = "movie_state";
            public static final String ZIP = "movie_zip";
        }

    }
}