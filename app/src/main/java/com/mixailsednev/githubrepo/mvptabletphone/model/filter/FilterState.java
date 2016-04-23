package com.mixailsednev.githubrepo.mvptabletphone.model.filter;

import android.support.annotation.Nullable;

public class FilterState {

    private Filter filter;

    public void setFilter(@Nullable Filter filter) {
        this.filter = filter;
    }

    @Nullable
    public Filter getFilter() {
        return filter;
    }
}
