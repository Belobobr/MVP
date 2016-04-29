package com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases;

import android.support.annotation.NonNull;

import com.mixailsednev.githubrepo.mvptabletphone.model.BaseStore;
import com.mixailsednev.githubrepo.mvptabletphone.model.cases.Case;

import java.util.ArrayList;
import java.util.List;

public class FilteredCasesStore extends BaseStore<FilteredCasesState> {

    private static String TAG = FilteredCasesStore.class.getSimpleName();

    private static FilteredCasesStore instance = new FilteredCasesStore();

    public static FilteredCasesStore getInstance() {
        return instance;
    }

    public FilteredCasesStore() {
        super(new FilteredCasesState(new ArrayList<Case>(), false));
    }

    public void setFilteredCases(@NonNull List<Case> cases) {
        getState().setFilteredCases(cases);
        notifyDataChanged();
    }

    public void setLoading(boolean loading) {
        getState().setLoading(loading);
        notifyDataChanged();
    }
}
