package br.udacity.controllers.detailImpl;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import br.udacity.connection.BaseImpl;
import br.udacity.connection.GenericRestCallBack;
import br.udacity.connection.interfaces.OnSucess;
import br.udacity.connection.interfaces.RetrofitInterface;
import br.udacity.models.response.ReviewsResponse;
import br.udacity.models.response.VideosResponse;

/**
 * Created by Jeferson on 29/04/2017.
 */

public class DetailImpl extends BaseImpl implements RetrofitInterface {

    private Context context;
    public DetailImpl(Context context) {
        this.context = context;
    }

    public void getVideos(String movieID, OnSucess onSucessListener, View progress, TextView nothing){
        new GenericRestCallBack<VideosResponse>().request(getMyContext(), getApiService().getVideos(movieID),onSucessListener, progress, nothing);
    }

    public void getReviews(String movieID,OnSucess onSucessListener, View progress, TextView nothing){
        new GenericRestCallBack<ReviewsResponse>().request(getMyContext(), getApiService().getReviews(movieID),onSucessListener, progress, nothing);
    }


    @Override
    protected Context getMyContext() {
        return context;
    }

    @Override
    protected DetailEndpoint getApiService() {
        return retrofit.create(DetailEndpoint.class);
    }
}
