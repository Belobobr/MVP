package com.mixailsednev.githubrepo.mvptabletphone.FilteredCases;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mixailsednev.githubrepo.mvptabletphone.common.BasePresenter;
import com.mixailsednev.githubrepo.mvptabletphone.model.CompositeDataChangeListener;
import com.mixailsednev.githubrepo.mvptabletphone.model.DataChangeListener;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.FilterState;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.FilterStore;
import com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases.FilteredCasesState;
import com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases.FilteredCasesStore;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.Filter;
import com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases.LoadCasesAction;

public class FilteredCasesPresenter extends BasePresenter<FilteredCasesView>
{
    private FilteredCasesStore filteredCasesStore;
    private FilterStore filterStore;
    private LoadCasesAction loadCasesAction;

    public FilteredCasesPresenter(FilteredCasesView filteredCasesView,
                                  FilteredCasesStore filteredCasesStore,
                                  FilterStore filterStore,
                                  LoadCasesAction loadCasesAction)
    {
        super(filteredCasesView);
        this.filteredCasesStore = filteredCasesStore;
        this.filterStore = filterStore;
        this.loadCasesAction = loadCasesAction;
    }

    @Override
    public void subscribeToDataStoreInternal(@NonNull CompositeDataChangeListener compositeDataChangeListener) {
        compositeDataChangeListener.addListener(filteredCasesStore, new DataChangeListener<FilteredCasesState>() {
            @Override
            public void newDataReceived(FilteredCasesState filteredCasesState) {
                FilteredCasesPresenter.this.getView().setCases(filteredCasesState.getFilteredCases());
                FilteredCasesPresenter.this.getView().setLoading(filteredCasesState.isLoading());
            }
        });
        compositeDataChangeListener.addListener(filterStore, new DataChangeListener<FilterState>() {
            @Override
            public void newDataReceived(FilterState filterState) {
                FilteredCasesPresenter.this.getView().setFilter(filterState.getFilter());
            }
        });
    }

    public void loadCases(@Nullable Filter filter) {
        loadCasesAction.run(filter);
    }

    public void selectFilter() {
        getView().selectFilter(filterStore.getState().getFilter());
    }

    public void saveFilter(@Nullable Filter filter) {
        filterStore.updateFilter(filter);
    }
}
