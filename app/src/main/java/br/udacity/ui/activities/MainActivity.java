package br.udacity.ui.activities;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.udacity.R;
import br.udacity.components.CustomTextView;
import br.udacity.connection.interfaces.OnSucess;
import br.udacity.controllers.mainImpl.MainImpl;
import br.udacity.data.MoviesContract;
import br.udacity.models.response.MoviesResponse;
import br.udacity.models.response.ResultResponse;
import br.udacity.ui.adapters.MoviesAdapter;
import br.udacity.ui.bases.BaseActivity;
import retrofit2.Response;

import static br.udacity.ui.activities.MainActivity.SortMovies.CLASSIFICATION;
import static br.udacity.ui.activities.MainActivity.SortMovies.FAVORITE;
import static br.udacity.ui.activities.MainActivity.SortMovies.POPULAR;

public class MainActivity extends BaseActivity implements MoviesAdapter.OnItemClick {

    private RecyclerView rcMovies;
    private SwipeRefreshLayout swipeView;
    private CustomTextView tvNothing;
    final static String MOVIE = "MOVIE";
    final static String ENUM = "ENUM";
    private SortMovies sort;

    ContentResolver contentResolver;
    Cursor cursor;


    @Override
    protected void setLayout(View view) {
        rcMovies = (RecyclerView) fid(R.id.rc_movies);
        swipeView = (SwipeRefreshLayout) fid(R.id.swipe);
        tvNothing = (CustomTextView) fid(R.id.tv_nothing);
    }

    @Override
    protected void startProperties() {
        setToolbar(false, getString(R.string.str_app_name));
        swipeView.setColorSchemeColors(
                ContextCompat.getColor(getMyContext(), R.color.colorAccent),
                ContextCompat.getColor(getMyContext(), R.color.red_gplus),
                ContextCompat.getColor(getMyContext(), R.color.darker_gray)
        );

    }

    @Override
    protected void onResume() {
        super.onResume();
        contentResolver = this.getContentResolver();
        if (savedInstanceState != null)
            sort = (SortMovies) savedInstanceState.getSerializable(ENUM);
        defineSort();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ENUM, sort);
        super.onSaveInstanceState(outState);
    }


    private void defineSort() {
        if (sort == null)
            sort = CLASSIFICATION;
        switch (sort) {
            case POPULAR:
                request(true, swipeView);
                break;
            case CLASSIFICATION:
                request(false, swipeView);
                break;
            case FAVORITE:
                favoriteList();
                break;
            default:
                request(false, swipeView);
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (cursor != null) cursor.close();
    }


    //Faz a requisição ao serviço de featured
    private void request(boolean isPopular, final View progress) {

        OnSucess onSucess = new OnSucess() {
            @Override
            public void onSucessResponse(Response response) {
                if (response != null && response.body() != null) {
                    MoviesResponse moviesResponse = (MoviesResponse) response.body();
                    if (moviesResponse != null && moviesResponse.getResults() != null && !moviesResponse.getResults().isEmpty()) {

                        List<ResultResponse> items = moviesResponse.getResults();
                        fillLists(rcMovies, new MoviesAdapter(items, MainActivity.this));
                    }
                }
            }
        };

        if (isPopular) {
            getControllerImpl().getPopular(onSucess, progress, tvNothing);
        } else {
            getControllerImpl().getTopRated(onSucess, progress, tvNothing);
        }

    }


    private void fillLists(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
        GridLayoutManager grid = new GridLayoutManager(getMyContext(), numberOfColumns());
        recyclerView.setLayoutManager(grid);

    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }


    @Override
    protected void defineListeners() {
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                defineSort();
            }
        });
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected Context getMyContext() {
        return MainActivity.this;
    }

    @Override
    protected MainImpl getControllerImpl() {
        return new MainImpl(getMyContext());
    }

    @Override
    public void onItemClick(View view, ResultResponse item) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MOVIE, item);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.menu_sort, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_menu:
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, getToolbar(), Gravity.RIGHT);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.menu_sort_options, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem subItem) {
                        switch (subItem.getItemId()) {
                            case R.id.popular:
                                sort = POPULAR;
                                request(true, swipeView);
                                break;
                            case R.id.classification:
                                sort = CLASSIFICATION;
                                request(false, swipeView);
                                break;
                            case R.id.favorite:
                                sort = FAVORITE;
                                favoriteList();
                                break;
                        }
                        return true;
                    }
                });

                popup.show(); //showing popup menu
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void favoriteList(){
        List<ResultResponse> items = new ArrayList<>();
        items = loadContent();
        fillLists(rcMovies, new MoviesAdapter(items, MainActivity.this));
        tvNothing.setVisibility(items.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private List<ResultResponse> loadContent() {
        cursor = this.getContentResolver().query(MoviesContract.Movie.CONTENT_URI, null, null, null, null);
        List<ResultResponse> items = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ResultResponse resultResponse = new ResultResponse(
                        cursor.getString(cursor.getColumnIndex(MoviesContract.Movie.Cols.MOVIE_ID)),
                        cursor.getDouble(cursor.getColumnIndex(MoviesContract.Movie.Cols.VOTE_AVERAGE)),
                        cursor.getString(cursor.getColumnIndex(MoviesContract.Movie.Cols.TITLE)),
                        cursor.getString(cursor.getColumnIndex(MoviesContract.Movie.Cols.POSTER)),
                        cursor.getString(cursor.getColumnIndex(MoviesContract.Movie.Cols.LANGUAGE)),
                        cursor.getString(cursor.getColumnIndex(MoviesContract.Movie.Cols.ORIGINAL_TITLE)),
                        cursor.getString(cursor.getColumnIndex(MoviesContract.Movie.Cols.PLOT)),
                        cursor.getString(cursor.getColumnIndex(MoviesContract.Movie.Cols.RELEASE_DATE)));
                items.add(resultResponse);
            } while (cursor.moveToNext());
        }
        return items;
    }

    public enum SortMovies {
        POPULAR,
        CLASSIFICATION,
        FAVORITE;
    }


}
