package br.udacity.controllers.detailImpl;

import android.content.Context;

import br.udacity.connection.BaseImpl;
import br.udacity.connection.interfaces.RetrofitInterface;

/**
 * Created by Jeferson on 29/04/2017.
 */

public class DetailImpl extends BaseImpl implements RetrofitInterface {

    private Context context;
    public DetailImpl(Context context) {
        this.context = context;
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
