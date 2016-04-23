package com.mixailsednev.githubrepo.mvptabletphone.model;

public interface DataChangeListener<Data> {
    void newDataReceived(Data data);
}
