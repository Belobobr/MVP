package com.mixailsednev.githubrepo.mvptabletphone.model;

import java.util.ArrayList;


//Implement store like behaviour subject.
public class BaseStore<Data>  {

    private Data mState;
    private ArrayList<DataChangeListener> mDataChangeListeners = new ArrayList<>();

    public BaseStore(Data data) {
        this.mState = data;
    }

    public Data getState() {
        return mState;
    }

    public void setState(Data state) {
        mState = state;
        notifyDataChanged(state);
    }

    public DataChangeListener addListener(DataChangeListener<Data> dataChangeListener) {
        mDataChangeListeners.add(dataChangeListener);
        dataChangeListener.newDataReceived(getState());
        return dataChangeListener;
    }

    public void removeListener(DataChangeListener<Data> dataDataChangeListener) {
        mDataChangeListeners.remove(dataDataChangeListener);
    }

    public void notifyDataChanged(Data data) {
        for (DataChangeListener listener : mDataChangeListeners) {
            listener.newDataReceived(data);
        }
    }
}
