package com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases;

import com.mixailsednev.githubrepo.mvptabletphone.model.BaseStore;
import com.mixailsednev.githubrepo.mvptabletphone.model.cases.Case;

import java.util.ArrayList;

//TODO move to di
public class FilteredCasesStore extends BaseStore<FilteredCasesState> {

    private static FilteredCasesStore instance = new FilteredCasesStore();

    public static FilteredCasesStore getInstance() {
        return instance;
    }

    public FilteredCasesStore() {
        super(new FilteredCasesState(new ArrayList<Case>(), false));
    }
}
