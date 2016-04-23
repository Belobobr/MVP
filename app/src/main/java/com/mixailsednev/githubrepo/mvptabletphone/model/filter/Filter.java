package com.mixailsednev.githubrepo.mvptabletphone.model.filter;

import java.io.Serializable;

public class Filter implements Serializable {

    private Long date;
    private String caseType;
    private String assigned;

    public Filter(Long date, String caseType, String assigned) {
        this.date = date;
        this.caseType = caseType;
        this.assigned = assigned;
    }

    public boolean isEmpty() {
        return this.date == null && this.caseType == null && this.assigned == null;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getAssigned() {
        return assigned;
    }

    public void setAssigned(String assigned) {
        this.assigned = assigned;
    }
}
