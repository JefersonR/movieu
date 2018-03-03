package br.udacity.ui.bases;

import android.support.v4.app.Fragment;

import br.udacity.R;

/**
 * Created by Jeferson on 10/09/2017.
 */

public abstract class BaseActivityForFragment extends BaseActivity {

    @Override
    protected int getActivityLayout() {
        return R.layout.layout_fragment;
    }

    @Override
    protected void startProperties() {
        if (savedInstanceState == null)
            setFragment(getInitFragment(), false);
        start();
    }

    protected abstract void start();

    protected abstract Fragment getInitFragment();
}
