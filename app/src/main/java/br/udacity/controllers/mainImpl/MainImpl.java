package br.udacity.controllers.mainImpl;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import br.udacity.connection.BaseImpl;
import br.udacity.connection.GenericRestCallBack;
import br.udacity.connection.interfaces.OnSucess;
import br.udacity.connection.interfaces.RetrofitInterface;
import br.udacity.models.response.MoviesResponse;

//Controller principal
public class MainImpl extends BaseImpl implements RetrofitInterface {

    private Context context;
    public MainImpl(Context context) {
        this.context = context;
    }

    public void getTopRated(OnSucess onSucessListener, View progress, TextView nothing){
        new GenericRestCallBack<MoviesResponse>().request(getMyContext(), getApiService().getTopRated(),onSucessListener, progress, nothing);
    }

    public void getPopular(OnSucess onSucessListener, View progress, TextView nothing){
        new GenericRestCallBack<MoviesResponse>().request(getMyContext(), getApiService().getPopular(),onSucessListener, progress, nothing);
    }

    @Override
    protected Context getMyContext() {
        return context;
    }

    @Override
    protected MainEndpoint getApiService() {
        return retrofit.create(MainEndpoint.class);
    }
}
