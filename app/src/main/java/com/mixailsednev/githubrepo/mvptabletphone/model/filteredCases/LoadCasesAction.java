package com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mixailsednev.githubrepo.mvptabletphone.model.cases.Case;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.Filter;
import java.util.List;

public class LoadCasesAction implements CasesServiceApi.CasesLoadedCallback {

    private FilteredCasesStore filteredCasesStore;
    private CasesServiceApi casesServiceApi;

    public LoadCasesAction(FilteredCasesStore filteredCasesStore, CasesServiceApi casesServiceApi) {
        this.filteredCasesStore = filteredCasesStore;
        this.casesServiceApi = casesServiceApi;
    }

    public void run(@Nullable Filter filter) {
        filteredCasesStore.setLoading(true);

        casesServiceApi.loadCases(filter, this);
    }

    @Override
    public void onCasesLoaded(@NonNull List<Case> loadedCases) {
        filteredCasesStore.setFilteredCases(loadedCases);
        filteredCasesStore.setLoading(false);
    }
}
