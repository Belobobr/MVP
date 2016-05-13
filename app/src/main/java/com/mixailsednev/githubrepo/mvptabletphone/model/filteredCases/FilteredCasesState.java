package com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases;

import android.support.annotation.NonNull;
import com.mixailsednev.githubrepo.mvptabletphone.model.cases.Case;

import java.util.ArrayList;
import java.util.List;

public class FilteredCasesState {

    private List<Case> filteredCases = new ArrayList<>();
    private boolean loading;

    public FilteredCasesState(List<Case> filteredCases, boolean loading) {
        this.filteredCases = filteredCases;
        this.loading = loading;
    }

    public void setFilteredCases(@NonNull List<Case> cases) {
        filteredCases = cases;
    }

    @NonNull
    public List<Case> getFilteredCases() {
        return filteredCases;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public boolean isLoading() {
        return loading;
    }
}
