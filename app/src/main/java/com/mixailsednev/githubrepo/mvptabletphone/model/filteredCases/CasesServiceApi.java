package com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mixailsednev.githubrepo.mvptabletphone.model.cases.Case;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.Filter;

import java.util.List;

public interface CasesServiceApi {

    interface CasesLoadedCallback {
        void onCasesLoadedFromApi(@NonNull List<Case> loadedCases);
    }

    void loadCases(@Nullable Filter filter, CasesLoadedCallback casesLoadedCallback);

}
