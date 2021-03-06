package com.mixailsednev.githubrepo.mvptabletphone.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity<Presenter extends BasePresenter> extends AppCompatActivity {

    private static String ARG_RESTORED = "RESTORED";
    protected Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = getPresenter();
    }

    public abstract Presenter getPresenter();

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ARG_RESTORED, true);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState == null || !savedInstanceState.getBoolean(ARG_RESTORED)) {
            onNewViewStateInstance();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPresenter().subscribeToDataStore();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getPresenter().unSubscribeFromDataStore();
    }

    /**
     * Called if a new state has been created because no viewstate from a previous
     * Activity or Fragment instance could be restored.
     * <p><b>Typically this is called on the first time the <i>Activity</i> or <i>Fragment</i> starts
     * and therefore no view state instance previously exists</b></p>
     */
    public void onNewViewStateInstance() {

    }
}
