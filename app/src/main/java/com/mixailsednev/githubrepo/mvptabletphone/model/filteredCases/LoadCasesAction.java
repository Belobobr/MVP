package com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mixailsednev.githubrepo.mvptabletphone.model.cases.Case;
import com.mixailsednev.githubrepo.mvptabletphone.model.cases.FilteredCaseDao;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.Filter;

import java.util.List;

public class LoadCasesAction implements CasesServiceApi.CasesLoadedCallback {

    private FilteredCasesStore filteredCasesStore;
    private CasesServiceApi casesServiceApi;

    public LoadCasesAction(FilteredCasesStore filteredCasesStore, CasesServiceApi casesServiceApi) {
        this.filteredCasesStore = filteredCasesStore;
        this.casesServiceApi = casesServiceApi;
    }


    public void run(@Nullable final Filter filter) {
        filteredCasesStore.setLoading(true);

        loadLocalCases(filter);
    }

    private void loadLocalCases(@Nullable final Filter filter) {
        new AsyncTask<Void, Integer, List<Case>>() {
            @Override
            protected List<Case> doInBackground(Void... params) {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException exception) {

                }
                return FilteredCaseDao.getCases(filter);
            }

            @Override
            protected void onPostExecute(List<Case> cases) {
                onLocalCasesLoaded(cases, filter);
            }
        }.execute();
    }

    private void onLocalCasesLoaded(@NonNull List<Case> loadedCases, @Nullable Filter filter) {
        final boolean online = false;

        filteredCasesStore.setFilteredCases(loadedCases);
        filteredCasesStore.setLoading(false);

        if (online) {
            casesServiceApi.loadCases(filter, LoadCasesAction.this);
        }
    }

    @Override
    public void onCasesLoadedFromApi(@NonNull List<Case> loadedCases) {
        filteredCasesStore.setLoading(false);

        new AsyncTask<Case, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Case... params) {
               return FilteredCaseDao.saveCases(params);
            }
        }.execute(loadedCases.toArray(new Case[loadedCases.size()]));
    }
}
