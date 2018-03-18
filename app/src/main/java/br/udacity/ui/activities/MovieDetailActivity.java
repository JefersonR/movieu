package br.udacity.ui.activities;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import br.udacity.R;
import br.udacity.connection.interfaces.OnSucess;
import br.udacity.controllers.detailImpl.DetailImpl;
import br.udacity.data.MoviesContentProvider;
import br.udacity.data.MoviesContract;
import br.udacity.models.response.ResultResponse;
import br.udacity.models.response.ReviewsResponse;
import br.udacity.models.response.VideosResponse;
import br.udacity.ui.adapters.ReviewAdapter;
import br.udacity.ui.adapters.TrailerAdapter;
import br.udacity.ui.bases.BaseActivity;
import br.udacity.utils.DateUtil;
import retrofit2.Response;

import static br.udacity.ui.activities.MainActivity.MOVIE;

public class MovieDetailActivity extends BaseActivity implements TrailerAdapter.OnItemClick {

    //UI elements
    private CollapsingToolbarLayout collapsingToolbar;
    private ProgressBar progressImg;
    private ImageView imgPoster;
    private TextView txtTitle;
    private TextView txtOriginal;
    private TextView txtReleased;
    private TextView txtLanguage;
    private ImageView bgheader;
    private TextView txtReviews;
    private TextView txtPlot;
    private RecyclerView rcVideos;
    private RecyclerView rcReviews;
    private ImageView imgFavorite;

    private ResultResponse movie;
    ContentResolver contentResolver;
    Cursor cursor;
    MoviesContentProvider provider;

    @Override
    protected DetailImpl getControllerImpl() {
        return new DetailImpl(getMyContext());
    }

    @Override
    public void setLayout(View view) {
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        progressImg = (ProgressBar) findViewById(R.id.progress_img);
        imgPoster = (ImageView) findViewById(R.id.img_poster);
        imgFavorite = (ImageView) findViewById(R.id.img_favorite);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        txtOriginal = (TextView) findViewById(R.id.txt_original);
        txtReleased = (TextView) findViewById(R.id.txt_released);
        txtLanguage = (TextView) findViewById(R.id.txt_language);
        bgheader = (ImageView) findViewById(R.id.bgheader);
        txtReviews = (TextView) findViewById(R.id.txt_reviews);
        txtPlot = (TextView) findViewById(R.id.txt_plot);
        rcVideos = (RecyclerView) findViewById(R.id.rc_videos);
        rcReviews = (RecyclerView) findViewById(R.id.rc_reviews);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(MOVIE, movie);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void startProperties() {
        setHasToolbar(false);
        setToolbar(R.id.toolbar, "", true);
        if (savedInstanceState != null) {
            movie = savedInstanceState.getParcelable(MOVIE);
        }
        contentResolver = this.getContentResolver();
        provider = new MoviesContentProvider();

        if (getIntent() != null) {
            movie = getIntent().getParcelableExtra(MOVIE);
            populate(movie);
        }
        if (!provider.contains(movie.getId(), MovieDetailActivity.this)) {
            imgFavorite.setImageResource(R.drawable.ic_favorite_empty);
        } else {
            imgFavorite.setImageResource(R.drawable.ic_favorite_full);
        }
    }


    @Override
    protected void defineListeners() {
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (provider.contains(movie.getId(), MovieDetailActivity.this)) {
                    imgFavorite.setImageResource(R.drawable.ic_favorite_empty);
                    contentResolver.delete(MoviesContract.Movie.CONTENT_URI, movie.getId(), null);

                } else {
                    imgFavorite.setImageResource(R.drawable.ic_favorite_full);
                    saveContent(movie);
                }

            }
        });
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected Context getMyContext() {
        return MovieDetailActivity.this;
    }

    private void populate(ResultResponse movie) {
        getReviews(movie);
        getVideos(movie);
        header(movie);
        details(movie);
    }

    private void details(ResultResponse movie) {
        final String str_empty = "N/A";
        txtTitle.setText(movie.getTitle());
        String originalTitle = String.format(getString(R.string.str_title_original), movie.getOriginalTitle());
        txtOriginal.setText(originalTitle);
        String released = "";
        try {
            released = String.format(getString(R.string.str_release_date), DateUtil.getDate(movie.getReleaseDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        txtReleased.setText(released);
        String language = String.format(getString(R.string.str_language), movie.getOriginalLanguage());
        txtLanguage.setText(language);
        txtReviews.setText(String.format(getString(R.string.str_average), movie.getVoteAverage()));
        txtPlot.setText(movie.getOverview().equals(str_empty) ? getString(R.string.str_no_info) : movie.getOverview());
    }

    private void saveContent(ResultResponse movie) {
        ContentValues val = new ContentValues();
        val.put(MoviesContract.Movie.Cols.MOVIE_ID, movie.getId());
        val.put(MoviesContract.Movie.Cols.TITLE, movie.getTitle());
        val.put(MoviesContract.Movie.Cols.ORIGINAL_TITLE, movie.getOriginalTitle());
        val.put(MoviesContract.Movie.Cols.RELEASE_DATE, movie.getReleaseDate());
        val.put(MoviesContract.Movie.Cols.VOTE_AVERAGE, movie.getVoteAverage());
        val.put(MoviesContract.Movie.Cols.PLOT, movie.getOverview());
        val.put(MoviesContract.Movie.Cols.LANGUAGE, movie.getOriginalLanguage());
        val.put(MoviesContract.Movie.Cols.POSTER, movie.getPosterPath());
        contentResolver.insert(MoviesContract.Movie.CONTENT_URI, val);
    }

    private void header(ResultResponse movie) {

        collapsingToolbar.setTitle(movie.getTitle());
        Picasso.with(MovieDetailActivity.this)
                .load(String.format(getString(R.string.str_base_img), movie.getPosterPath()))
                .error(R.drawable.ic_poster_standart)
                .into(imgPoster, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        progressImg.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        progressImg.setVisibility(View.GONE);
                    }
                });

        Picasso.with(MovieDetailActivity.this)
                .load(String.format(getString(R.string.str_base_img_big), movie.getPosterPath()))
                .error(R.drawable.ic_poster_standart)
                .into(bgheader);
    }

    private void getReviews(ResultResponse movie) {
        getControllerImpl().getReviews(movie.getId(), new OnSucess() {
            @Override
            public void onSucessResponse(Response response) {
                ReviewsResponse reviewsResponse = (ReviewsResponse) response.body();
                if (reviewsResponse != null && reviewsResponse.getResults() != null && !reviewsResponse.getResults().isEmpty()) {
                    fillLists(rcReviews, new ReviewAdapter(reviewsResponse.getResults()));
                }
            }
        }, null, null);
    }

    private void getVideos(ResultResponse movie) {
        getControllerImpl().getVideos(movie.getId(), new OnSucess() {
            @Override
            public void onSucessResponse(Response response) {
                VideosResponse videosResponse = (VideosResponse) response.body();
                List<VideosResponse.Result> list = new ArrayList<>();
                if (videosResponse != null && videosResponse.getResults() != null && !videosResponse.getResults().isEmpty()) {
                    for (VideosResponse.Result result : videosResponse.getResults()) {
                        if (result.getType().equals(getString(R.string.str_trailer))) {
                            list.add(result);
                        }
                    }
                }
                fillLists(rcVideos, new TrailerAdapter(list, MovieDetailActivity.this));


            }
        }, null, null);
    }

    private void fillLists(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getMyContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void onItemClick(View view, VideosResponse.Result item) {
        playVideo(item.getKey());
    }

    public void playVideo(final String vID) {
        final String urlBase = "https://www.youtube.com/watch?v=%s";
        final String uriBase = "vnd.youtube:%s";
        final String full = "force_fullscreen";


        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(uriBase, vID)));
        appIntent.putExtra(full, true);
        appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivityForResult(appIntent, 1);
        } catch (Exception ex) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(String.format(urlBase, vID)));
            webIntent.putExtra(full, true);
            webIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(webIntent, 1);
        }
    }


}




