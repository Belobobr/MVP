package com.mixailsednev.githubrepo.mvptabletphone.model.filter;

import android.support.annotation.Nullable;

import java.io.Serializable;

public class Filter implements Serializable {

    @Nullable
    private Long date;
    @Nullable
    private String caseType;
    @Nullable
    private String assigned;

    public Filter(@Nullable Long date, @Nullable String caseType, @Nullable String assigned) {
        this.date = date;
        this.caseType = caseType;
        this.assigned = assigned;
    }

    public boolean isEmpty() {
        return this.date == null && this.caseType == null && this.assigned == null;
    }

    @Nullable
    public Long getDate() {
        return date;
    }

    public void setDate(@Nullable Long date) {
        this.date = date;
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
}
