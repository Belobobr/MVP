package com.mixailsednev.githubrepo.mvptabletphone.model;

import com.mixailsednev.githubrepo.mvptabletphone.Injection;
import com.mixailsednev.githubrepo.mvptabletphone.model.filteredCases.LoadCasesAction;

public class ActionCreator {

    private static ActionCreator instance = new ActionCreator();

    public static ActionCreator getInstance() {
        return instance;
    }

    public ActionCreator() {
    }

    public LoadCasesAction createLoadFilteredCasesAction() {
        return new LoadCasesAction(Injection.provideFilteredCasesStore(), Injection.provideCasesServiceApi());
    }

}
