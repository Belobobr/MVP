package com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases;

import android.support.annotation.Nullable;

import com.mixailsednev.githubrepo.mvptabletphone.model.cases.Case;
import com.mixailsednev.githubrepo.mvptabletphone.model.cases.CaseBuilder;
import com.mixailsednev.githubrepo.mvptabletphone.model.cases.CaseTypes;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.Filter;
import com.mixailsednev.githubrepo.mvptabletphone.utils.MsCollectionsUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;

public class CasesServiceApiImp implements  CasesServiceApi {

    private static CasesServiceApiImp instance = new CasesServiceApiImp();

    public static CasesServiceApiImp getInstance() {
        return instance;
    }

    @Override
    public Observable<List<Case>> loadCases(@Nullable Filter filter) {
        return Observable.create(subscriber ->  {
            Observable.just(loadCasesInternal(filter)).subscribe(subscriber);
        });
    }

    private List<Case> loadCasesInternal(@Nullable Filter filter) {
        List<Case> cases = new ArrayList<>(
                Arrays.asList(
                        new CaseBuilder()
                                .setTitle("First")
                                .setCaseType(CaseTypes.COMMON)
                                .createCase(),
                        new CaseBuilder()
                                .setTitle("Second")
                                .setCaseType(CaseTypes.COMMON)
                                .setAssigned("Misha")
                                .createCase(),
                        new CaseBuilder()
                                .setTitle("Third")
                                .setCaseType(CaseTypes.COMMON)
                                .setAssigned("Misha")
                                .createCase(),
                        new CaseBuilder()
                                .setTitle("fourth")
                                .setCaseType(CaseTypes.ARBITR)
                                .createCase())
        );

        if (filter == null || filter.isEmpty()) {
            return cases;
        } else {
            cases =  MsCollectionsUtils.filter(cases, new Case.CaseTypePredicate(filter.getCaseType()));
            cases =  MsCollectionsUtils.filter(cases, new Case.AssignedPredicate(filter.getAssigned()));
            return cases;
        }
    }
}
