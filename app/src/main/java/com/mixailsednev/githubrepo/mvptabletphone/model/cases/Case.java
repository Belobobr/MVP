package com.mixailsednev.githubrepo.mvptabletphone.model.cases;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.mixailsednev.githubrepo.mvptabletphone.utils.MsCollectionsUtils;

public class Case {

    @Nullable
    private String title;
    @Nullable
    private String caseType;
    @Nullable
    private String assigned;

    public Case(@Nullable String title, @Nullable String caseType, @Nullable String assigned) {
        this.title = title;
        this.caseType = caseType;
        this.assigned = assigned;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Nullable
    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    @Nullable
    public String getAssigned() {
        return assigned;
    }

    public void setAssigned(String assigned) {
        this.assigned = assigned;
    }

    @Override
    public String toString() {
        return "Case: " + title + ". Type: " + caseType + ". Assigned: " + assigned;
    }

    public static class CaseTypePredicate implements MsCollectionsUtils.IPredicate<Case> {

        @Nullable
        private String caseType;

        public CaseTypePredicate(@Nullable String caseType) {
            this.caseType = caseType;
        }

        public boolean apply(Case aCase) {
            return TextUtils.isEmpty(caseType) || caseType.equals(aCase.getCaseType());
        }
    }

    public static class AssignedPredicate implements MsCollectionsUtils.IPredicate<Case> {

        @Nullable
        private String assigned;

        public AssignedPredicate(@Nullable String assigned) {
            this.assigned = assigned;
        }

        public boolean apply(Case aCase) {
            return TextUtils.isEmpty(assigned) ||  assigned.equals(aCase.getAssigned());
        }
    }

}
