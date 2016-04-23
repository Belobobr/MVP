package com.mixailsednev.githubrepo.mvptabletphone.filter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.mixailsednev.githubrepo.mvptabletphone.model.filter.Filter;
import com.mixailsednev.githubrepo.mvptabletphone.R;

public class TabletFilterView extends LinearLayout{

    private Filter filter;
    private FilterSelectedCallback filterSelectedCallback;

    public TabletFilterView(Context context) {
        this(context, null);
    }

    public TabletFilterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabletFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_tablet_filter, this, true);
    }

    public void setFilterSelectedCallback(FilterSelectedCallback filterSelectedCallback) {
        this.filterSelectedCallback = filterSelectedCallback;
    }

    public void onFilterUpdated(Filter filter) {
        filterSelectedCallback.onFilterSelected(filter);
    }

    public void setFilter(@Nullable Filter filter) {
        this.filter = filter;
    }
}
