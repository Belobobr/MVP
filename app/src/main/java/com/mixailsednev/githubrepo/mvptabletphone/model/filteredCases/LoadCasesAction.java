package com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases;

import android.support.annotation.Nullable;

import com.mixailsednev.githubrepo.mvptabletphone.model.cases.FilteredCaseDao;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.Filter;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoadCasesAction {

    private FilteredCasesStore filteredCasesStore;
    private CasesServiceApi casesServiceApi;

    public LoadCasesAction(FilteredCasesStore filteredCasesStore, CasesServiceApi casesServiceApi) {
        this.filteredCasesStore = filteredCasesStore;
        this.casesServiceApi = casesServiceApi;
    }

    public void run(@Nullable final Filter filter) {
        filteredCasesStore.setLoading(true);

        FilteredCaseDao.getCasesObservable(filter)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(localCases -> {
                    filteredCasesStore.setFilteredCases(localCases);
                    filteredCasesStore.setLoading(false);

                    return casesServiceApi.loadCases(filter)
                            .subscribeOn(Schedulers.newThread());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(remoteCases -> {
                    filteredCasesStore.setFilteredCases(remoteCases);
                    FilteredCaseDao.saveCases(remoteCases);
                });
    }
}
