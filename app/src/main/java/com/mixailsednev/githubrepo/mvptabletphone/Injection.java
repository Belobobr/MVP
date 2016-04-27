package com.mixailsednev.githubrepo.mvptabletphone;

import com.mixailsednev.githubrepo.mvptabletphone.model.ActionCreator;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.FilterStore;
import com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases.CasesServiceApi;
import com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases.CasesServiceApiImp;
import com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases.FilteredCasesStore;

public class Injection {

    public static FilteredCasesStore provideFilteredCasesStore() {
        return FilteredCasesStore.getInstance();
    }

    public static FilterStore provideFilterStore() {
        return FilterStore.getInstance();
    }

    public static ActionCreator provideActionCreator() {
        return ActionCreator.getInstance();
    }

    public static CasesServiceApi provideCasesServiceApi() {
        return CasesServiceApiImp.getInstance();
    }

}
