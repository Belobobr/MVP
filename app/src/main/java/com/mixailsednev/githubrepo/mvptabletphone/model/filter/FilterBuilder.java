package com.mixailsednev.githubrepo.mvptabletphone.model.filter;

public class FilterBuilder {
    private Long date;
    private String caseType;
    private String assigned;

    public FilterBuilder setDate(Long date) {
        this.date = date;
        return this;
    }

    public FilterBuilder setCaseType(String caseType) {
        this.caseType = caseType;
        return this;
    }

    public FilterBuilder setAssigned(String assigned) {
        this.assigned = assigned;
        return this;
    }

    public Filter createFilter() {
        return new Filter(date, caseType, assigned);
    }
}