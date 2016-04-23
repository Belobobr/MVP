package com.mixailsednev.githubrepo.mvptabletphone.cases;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.mixailsednev.githubrepo.mvptabletphone.model.cases.Case;

import java.util.ArrayList;

public class CasesAdapter extends ArrayAdapter<Case> {

    public CasesAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1, new ArrayList<Case>());
    }
}
