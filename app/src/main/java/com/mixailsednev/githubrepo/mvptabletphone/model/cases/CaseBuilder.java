package com.mixailsednev.githubrepo.mvptabletphone.model.cases;

import android.support.annotation.Nullable;

public class CaseBuilder {
    @Nullable
    private String title;
    @Nullable
    private String caseType;
    @Nullable
    private String assigned;

    public CaseBuilder setTitle(@Nullable String title) {
        this.title = title;
        return this;
    }

    public CaseBuilder setCaseType(@Nullable String caseType) {
        this.caseType = caseType;
        return this;
    }

    public CaseBuilder setAssigned(@Nullable String assigned) {
        this.assigned = assigned;
        return this;
    }

    public Case createCase() {
        return new Case(title, caseType, assigned);
    }
}