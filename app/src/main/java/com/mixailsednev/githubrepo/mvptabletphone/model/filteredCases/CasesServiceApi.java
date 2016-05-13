package com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mixailsednev.githubrepo.mvptabletphone.model.cases.Case;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.Filter;

import java.util.List;

import rx.Observable;

public interface CasesServiceApi {

    interface CasesLoadedCallback {
        void onCasesLoadedFromApi(@NonNull List<Case> loadedCases);
    }

    Observable<List<Case>> loadCases(@Nullable Filter filter);

}
