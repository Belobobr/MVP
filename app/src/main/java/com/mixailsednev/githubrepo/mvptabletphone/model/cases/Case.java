package com.mixailsednev.githubrepo.mvptabletphone.model.cases;

import com.mixailsednev.githubrepo.mvptabletphone.utils.MsCollectionsUtils;

public class Case {

    private String title;
    private String caseType;

    public Case(String title) {
        this(title, CaseTypes.COMMON);
    }

    public Case(String title, String caseType) {
        this.title = title;
        this.caseType = caseType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    @Override
    public String toString() {
        return "Title: " + title + " case type: " + caseType;
    }

    public static class CommonCasePredicate implements MsCollectionsUtils.IPredicate<Case> {
        public boolean apply(Case aCase) {
            return aCase.getCaseType().equals(CaseTypes.COMMON);
        }
    }

}
