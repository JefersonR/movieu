package br.udacity.ui.activities;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;

import br.udacity.R;
import br.udacity.controllers.detailImpl.DetailImpl;
import br.udacity.models.response.ResultResponse;
import br.udacity.ui.bases.BaseActivity;
import br.udacity.utils.DateUtil;

import static br.udacity.ui.activities.MainActivity.MOVIE;

public class MovieDetailActivity extends BaseActivity {

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

    @Override
    protected DetailImpl getControllerImpl() {
        return new DetailImpl(getMyContext());
    }

    @Override
    public void setLayout(View view) {
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        progressImg = (ProgressBar) findViewById(R.id.progress_img);
        imgPoster = (ImageView) findViewById(R.id.img_poster);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        txtOriginal = (TextView) findViewById(R.id.txt_original);
        txtReleased = (TextView) findViewById(R.id.txt_released);
        txtLanguage = (TextView) findViewById(R.id.txt_language);
        bgheader = (ImageView) findViewById(R.id.bgheader);
        txtReviews = (TextView) findViewById(R.id.txt_reviews);
        txtPlot = (TextView) findViewById(R.id.txt_plot);
    }

    @Override
    protected void startProperties() {
        setHasToolbar(false);
        setToolbar(R.id.toolbar, "", true);
        if (getIntent() != null) {
            ResultResponse movie = getIntent().getParcelableExtra(MOVIE);
            populate(movie);
        }
    }

    @Override
    protected void defineListeners() {

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
        final String str_empty = "N/A";
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
}




