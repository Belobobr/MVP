package com.mixailsednev.githubrepo.mvptabletphone.cases;
import android.support.annotation.NonNull;

import com.mixailsednev.githubrepo.mvptabletphone.model.cases.Case;

import java.util.List;

public interface CasesView {
    void setCases(@NonNull List<Case> cases);
    void setLoading(boolean loading);
}
