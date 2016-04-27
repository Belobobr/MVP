package com.mixailsednev.githubrepo.mvptabletphone.model.filter;

import android.support.annotation.Nullable;

import com.mixailsednev.githubrepo.mvptabletphone.model.BaseStore;

public class FilterStore extends BaseStore<FilterState> {
    private static FilterStore instance = new FilterStore();

    public static FilterStore getInstance() {
        return instance;
    }

    public FilterStore() {
        super(new FilterState());
    }

    public void updateFilter(@Nullable Filter filter) {
        getState().setFilter(filter);
        notifyDataChanged();
    }
}
