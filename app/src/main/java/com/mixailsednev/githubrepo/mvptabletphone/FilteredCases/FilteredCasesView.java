package com.mixailsednev.githubrepo.mvptabletphone.FilteredCases;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mixailsednev.githubrepo.mvptabletphone.model.cases.Case;
import com.mixailsednev.githubrepo.mvptabletphone.model.filter.Filter;

import java.util.List;

public interface FilteredCasesView {
    void setCases(@NonNull List<Case> cases);
    void setLoading(boolean loading);
    void setFilter(@Nullable Filter filter);
    void selectFilter(@Nullable Filter currentFilter);
}
