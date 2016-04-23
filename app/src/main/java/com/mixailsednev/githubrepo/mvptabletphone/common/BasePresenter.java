package com.mixailsednev.githubrepo.mvptabletphone.common;

public abstract class BasePresenter<View> {

    private View view;

    public BasePresenter(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void subscribeToDataStore() {

    }

    public void unSubscribeFromDataStore() {

    }
}
