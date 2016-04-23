package com.mixailsednev.githubrepo.mvptabletphone.FilteredCases;

import android.support.annotation.Nullable;
import android.util.Log;

import com.mixailsednev.githubrepo.mvptabletphone.common.BasePresenter;
import com.mixailsednev.githubrepo.mvptabletphone.model.CompositeDataChangeListener;
import com.mixailsednev.githubrepo.mvptabletphone.model.DataChangeListener;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.FilterState;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.FilterStore;
import com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases.FilteredCasesActions;
import com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases.FilteredCasesState;
import com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases.FilteredCasesStore;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.Filter;

public class FilteredCasesPresenter extends BasePresenter<FilteredCasesView>
{
    private CompositeDataChangeListener compositeDataChangeListener;

    public FilteredCasesPresenter(FilteredCasesView filteredCasesView) {
        super(filteredCasesView);
        compositeDataChangeListener = new CompositeDataChangeListener();
    }

    @Override
    public void subscribeToDataStore() {
        compositeDataChangeListener.addListener(FilteredCasesStore.getInstance(), new DataChangeListener<FilteredCasesState>() {
            @Override
            public void newDataReceived(FilteredCasesState filteredCasesState) {
                Log.i("FilteredCasesPresenter", "FilteredCasesPresenter: " + filteredCasesState.toString());
                FilteredCasesPresenter.this.getView().setCases(filteredCasesState.getFilteredCases());
                FilteredCasesPresenter.this.getView().setLoading(filteredCasesState.isLoading());
            }
        });
        compositeDataChangeListener.addListener(FilterStore.getInstance(), new DataChangeListener<FilterState>() {
            @Override
            public void newDataReceived(FilterState filterState) {
                FilteredCasesPresenter.this.getView().setFilter(filterState.getFilter());
            }
        });
        compositeDataChangeListener.subscribe();
    }

    @Override
    public void unSubscribeFromDataStore() {
        compositeDataChangeListener.unsubscribe();
    }

    public void filterSelected(@Nullable Filter filter) {
        FilteredCasesActions.filterSelected(filter);
    }
}
