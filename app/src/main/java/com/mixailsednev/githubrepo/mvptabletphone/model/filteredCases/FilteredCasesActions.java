package com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.mixailsednev.githubrepo.mvptabletphone.model.cases.Case;
import com.mixailsednev.githubrepo.mvptabletphone.model.cases.CaseTypes;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.Filter;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.FilterState;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.FilterStore;
import com.mixailsednev.githubrepo.mvptabletphone.utils.MsCollectionsUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilteredCasesActions {

    public static void filterSelected(@Nullable Filter filter) {
        FilteredCasesState filteredCasesState = FilteredCasesStore.getInstance().getState();
        filteredCasesState.setLoading(true);
        FilteredCasesStore.getInstance().setState(filteredCasesState);

        FilterState filterState = FilterStore.getInstance().getState();
        filterState.setFilter(filter);

        new AsyncTask<Filter, Integer, List<Case>>() {
            @Override
            protected List<Case> doInBackground(Filter... params) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException exception) {

                }

                final Filter filter = params[0];
                List<Case> cases = new ArrayList<>(Arrays.asList(new Case("First", CaseTypes.ARBITR), new Case("Second"), new Case("Third")));

                if (filter == null || filter.isEmpty()) {
                    return cases;
                } else {
                    return MsCollectionsUtils.filter(cases, new Case.CommonCasePredicate());
                }
            }

            @Override
            protected void onPostExecute(List<Case> cases) {
                FilteredCasesState filteredCasesState = FilteredCasesStore.getInstance().getState();
                filteredCasesState.setFilteredCases(cases);
                filteredCasesState.setLoading(false);
                FilteredCasesStore.getInstance().setState(filteredCasesState);
            }
        }.execute(filter);
    }
}
